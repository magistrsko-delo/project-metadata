package si.fri.mag.grpc.client;

import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.kumuluz.ee.grpc.client.GrpcChannelConfig;
import com.kumuluz.ee.grpc.client.GrpcChannels;
import com.kumuluz.ee.grpc.client.GrpcClient;
import grpc.AwsstorageGrpc;
import grpc.AwsstorageService;
import io.grpc.stub.StreamObserver;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.net.ssl.SSLException;
import javax.ws.rs.InternalServerErrorException;
import java.io.*;

@ApplicationScoped
public class AwsStorageServiceClientGrpc {
    private AwsstorageGrpc.AwsstorageStub awsstorageStub;
    @PostConstruct
    public void init() {
        try {
            GrpcChannels clientPool = GrpcChannels.getInstance();
            GrpcChannelConfig config = clientPool.getGrpcClientConfig("awsStorageClient");
            GrpcClient client = new GrpcClient(config);
            awsstorageStub = AwsstorageGrpc.newStub(client.getChannel());
        } catch (SSLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void uploadFileToAWS(File newMedia, String bucketName, String mediaName) {
        StreamObserver<AwsstorageService.UploadRequest> requestObserver = awsstorageStub.uploadFile(this.uploadStreamGrpcObserver(newMedia));
        try {
            try {
                BufferedInputStream bInputStream = new BufferedInputStream(new FileInputStream(newMedia));
                int bufferSize = 512 * 1024; // 512k
                byte[] buffer = new byte[bufferSize];
                int size = 0;
                while ((size = bInputStream.read(buffer)) > 0) {
                    ByteString byteString = ByteString.copyFrom(buffer, 0, size);
                    AwsstorageService.UploadRequest req = AwsstorageService.UploadRequest.newBuilder()
                            .setBucketname(bucketName)
                            .setMedianame(mediaName)
                            .setData(byteString)
                            .setOffset(size).build();
                    requestObserver.onNext(req);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw new InternalServerErrorException(e.getMessage());
        }
        requestObserver.onCompleted();
    }

    private StreamObserver<AwsstorageService.UploadResponse> uploadStreamGrpcObserver(File newMedia) {
        return new StreamObserver<AwsstorageService.UploadResponse>() {
            @Override
            public void onNext(AwsstorageService.UploadResponse value) {
            }

            @Override
            public void onError(Throwable t) {
                throw new InternalServerErrorException(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Client response onCompleted");
                newMedia.delete();
            }
        };
    }

}
