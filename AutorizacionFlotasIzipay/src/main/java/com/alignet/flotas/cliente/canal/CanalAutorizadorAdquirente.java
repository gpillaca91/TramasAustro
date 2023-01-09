package com.alignet.flotas.cliente.canal;

import java.io.IOException;
import java.net.ServerSocket;
import java.io.EOFException;
import java.net.SocketException;
import java.io.InterruptedIOException;
import java.net.Socket;
import org.jpos.util.Logger;

import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOFilter;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISOUtil;
import org.jpos.util.LogEvent;

public class CanalAutorizadorAdquirente extends BaseChannel {
	public CanalAutorizadorAdquirente() {
	}

	public CanalAutorizadorAdquirente(String host, int port, ISOPackager paquete) {
		super(host, port, paquete);
	}

	public CanalAutorizadorAdquirente(ISOPackager paquete) throws IOException {
		super(paquete);
	}

	public CanalAutorizadorAdquirente(ISOPackager paquete, ServerSocket socket) throws IOException {
		super(paquete, socket);
	}

	protected void sendMessageLength(int len) throws IOException {
		this.serverOut.write(len >> 8);
		this.serverOut.write(len);
	}

	protected int getMessageLength() throws IOException, ISOException {
		byte[] b = new byte[2];
		this.serverIn.readFully(b, 0, 2);
		return (b[0] & 0xFF) << 8 | b[1] & 0xFF;
	}

	public void setHeader(String header) {
		super.setHeader(ISOUtil.str2bcd(header, false));
	}

	public ISOMsg receive() throws IOException, ISOException {
		ISOMsg resp = super.receive();
		return resp;
	}
        
        
}
