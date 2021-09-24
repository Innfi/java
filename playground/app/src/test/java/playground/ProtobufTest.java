package playground;

import org.junit.Test;

import playground.RegisterUserOuterClass.RegisterUser;

import static org.junit.Assert.*;

import com.google.protobuf.ByteString;


public class ProtobufTest {
    @Test 
    public void testSerialize() throws Exception {
        String email = "innfi@test.com";
        String badge = "this";

        RegisterUser packet = RegisterUser.newBuilder()
            .setEmail(email)
            .setUserId("innfi")
            .addBadges(badge)
            .addBadges("is")
            .addBadges("a test")
            .build();
        
        ByteString serialized = packet.toByteString();
        RegisterUser afterPacket = RegisterUser.parseFrom(serialized);

        assertEquals(packet.getEmail(), afterPacket.getEmail());
        assertEquals(afterPacket.getBadges(0), badge);
    }
}
