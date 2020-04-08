package com.zp4rker.nprotocoltest

import com.nukkitx.protocol.bedrock.BedrockPong
import com.nukkitx.protocol.bedrock.BedrockServer
import com.nukkitx.protocol.bedrock.BedrockServerEventHandler
import com.nukkitx.protocol.bedrock.BedrockServerSession
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler
import com.nukkitx.protocol.bedrock.packet.LoginPacket
import com.nukkitx.protocol.bedrock.v389.Bedrock_v389
import java.net.InetSocketAddress

fun main() {
    val server = BedrockServer(InetSocketAddress(19132))

    val pong = BedrockPong().apply {
        edition = "MCPE"
        motd = "A test mcpe server"
        playerCount = 0
        maximumPlayerCount = 1
        gameType = "Survival"
        protocolVersion = Bedrock_v389.V389_CODEC.protocolVersion
    }

    server.handler = object: BedrockServerEventHandler {
        override fun onConnectionRequest(address: InetSocketAddress): Boolean {
            return true
        }

        override fun onSessionCreation(session: BedrockServerSession) {
            session.packetHandler = object: BedrockPacketHandler {
                override fun handle(packet: LoginPacket): Boolean {
                    println("either recieved or sent login packet")
                    return true
                }
            }
        }

        override fun onQuery(address: InetSocketAddress): BedrockPong {
            return pong
        }

    }

    server.bind().join()
}