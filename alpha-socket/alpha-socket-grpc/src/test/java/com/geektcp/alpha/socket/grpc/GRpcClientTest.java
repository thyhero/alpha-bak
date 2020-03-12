package com.geektcp.alpha.socket.grpc;

import com.geektcp.alpha.socket.grpc.proto.file.FileData;
import com.geektcp.alpha.socket.grpc.proto.file.FileServiceGrpc;
import com.geektcp.alpha.socket.grpc.proto.file.Response;
import com.geektcp.alpha.socket.grpc.proto.greet.GreetServiceGrpc;
import com.geektcp.alpha.socket.grpc.proto.greet.Greeting;
import com.geektcp.alpha.socket.grpc.proto.greet.Person;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author tanghaiyang on 2020/1/2 1:18.
 */
@Slf4j
public class GRpcClientTest {

    @Test
    public void startClientForGRpc(){
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 10000)
                .usePlaintext()
                .build();
        GreetServiceGrpc.GreetServiceBlockingStub stub = GreetServiceGrpc.newBlockingStub(channel);
        Person person = Person.newBuilder().setFirstName("tang").setLastName("hai").build();
        log.info("client sending {}", person);

        Greeting greeting = stub.sayHello(person);
        log.info("client received {}", greeting);
        Assert.assertTrue(true);
    }

    @Test
    public void startClientForByte(){
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 10000)
                .usePlaintext()
                .build();
        String filePath = "F:\\tmp\\upload\\src.txt";
        ByteString firstName = ByteString.copyFrom("tang".getBytes());
        FileServiceGrpc.FileServiceBlockingStub stub = FileServiceGrpc.newBlockingStub(channel);
        FileData fileData = FileData.newBuilder().setData(firstName).setMsg("hai").build();
        log.info("client sending {}", fileData);
        Response response = stub.send(fileData);
        log.info("client received {}", response);
        channel.shutdown();
        Assert.assertTrue(true);
    }

    @Test
    public void startClientForFile() throws Exception{
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 10000)
                .usePlaintext()
                .build();
        String srcFilePath = "/share/down/jdk-9+181_linux-x64_ri.zip";
        File srcFile = new File(srcFilePath);
        FileInputStream srcFis = new FileInputStream(srcFile);
        FileChannel srcFileChannel = srcFis.getChannel();
        FileServiceGrpc.FileServiceBlockingStub stub = FileServiceGrpc.newBlockingStub(channel);
        int len = 200000;
        int size = 0;
        int startPos = 0;
        ByteBuffer buffer = ByteBuffer.allocateDirect(len);
        while (true) {
            size = srcFileChannel.read(buffer);
            if (size == -1) {
                break;
            }
            buffer.flip();
            FileData fileData = FileData.newBuilder().
                    setData(ByteString.copyFrom(buffer))
                    .build();
//            log.info("client sending {}", fileData);
            Response response = stub.send(fileData);
            log.info("client received {}", response.getMsg());
            buffer.clear();
        }
        log.info("finished!");
        channel.shutdown();
        Assert.assertTrue(true);
    }

//    @Test
//    public void writeFileByteByChannel() {
//        try {
//            String srcFilePath = "/share/down/jdk-9+181_linux-x64_ri.zip";
//            String dstFilePath = "/share/down/file/jdk-9+181_linux-x64_ri.zip";
//            File srcFile = new File(srcFilePath);
//            FileInputStream srcFis = new FileInputStream(srcFile);
//            FileChannel srcFileChannel = srcFis.getChannel();
//            File dstFile = new File(dstFilePath);
//            FileOutputStream dstFos = new FileOutputStream(dstFile);
//            FileChannel dstFileChannel = dstFos.getChannel();
//            int len = 2000;
//            int size = 0;
//            ByteBuffer buffer = ByteBuffer.allocateDirect(len);
//            while (true) {
//                size = srcFileChannel.read(buffer);
//                if (size == -1) {
//                    break;
//                }
//                buffer.flip();
//                dstFileChannel.write(buffer);
//                buffer.clear();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Assert.assertTrue(true);
//    }
}
