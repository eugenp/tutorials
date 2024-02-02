package com.baeldung.netty.customhandlersandlisteners;

import java.util.Scanner;

import com.baeldung.netty.customhandlersandlisteners.handler.ClientEventHandler;
import com.baeldung.netty.customhandlersandlisteners.listener.ChannelInfoListener;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatClientMain {

    private static final String SYSTEM_USER = System.getProperty("user.name");
    private static String user;

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try (Scanner scanner = new Scanner(System.in)) {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                            .addFirst(new StringDecoder(), new ClientEventHandler(), new StringEncoder());
                    }
                });

            ChannelFuture future = bootstrap.connect(ChatServerMain.HOST, ChatServerMain.PORT)
                .sync();

            future.addListener(new ChannelInfoListener("connected to server"));
            Channel channel = future.sync()
                .channel();

            messageLoop(scanner, channel);

            channel.close();
        } catch (Throwable e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            group.shutdownGracefully();
        }
    }

    private static void messageLoop(Scanner scanner, Channel channel) throws InterruptedException {
        Thread.sleep(50);

        if (user == null) {
            System.out.printf("your name [%s]: ", SYSTEM_USER);
            user = scanner.nextLine();
            if (user.isEmpty())
                user = SYSTEM_USER;
        }

        System.out.print("> ");
        while (scanner.hasNext()) {
            String message = scanner.nextLine();
            if (message.equals("exit"))
                break;

            ChannelFuture sent = channel.writeAndFlush(user + ";" + message);
            sent.addListener(new ChannelInfoListener("message sent"));
            sent.addListener(future -> System.out.print("> "));
        }
    }
}