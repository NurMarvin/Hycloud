package de.nurmarvin.hycloud.master.networking;

import io.netty.buffer.ByteBuf;

public class StringUtil {

    public static String getString(ByteBuf byteBuf) {
        int length = byteBuf.readInt();

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < length; i++) {
            s.append(getStringFromBytes(byteBuf.readByte()));
        }
        return s.toString();
    }

    private static String getStringFromBytes(byte bytes) {
        return String.valueOf((char) bytes);
    }

    public static void writeString(String text, ByteBuf byteBuf) {
        byteBuf.writeInt(text.getBytes().length);
        byteBuf.writeBytes(text.getBytes());
    }

}
