package com.alignet.flotas.cliente.servicio;

import java.io.IOException;
import java.util.ResourceBundle;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

import com.alignet.flotas.cliente.canal.CanalAutorizadorAdquirente;
import com.alignet.flotas.cliente.mensaje.MensajeAnulacion;
import com.alignet.flotas.cliente.mensaje.MensajeAutorizacion;
import com.alignet.flotas.cliente.mensaje.MensajeReimpresion;
import com.alignet.flotas.cliente.mensaje.MensajeReporteTxs;
import com.alignet.flotas.cliente.util.ConvertidorMsgAnulacion;
import com.alignet.flotas.cliente.util.ConvertidorMsgAutorizacion;
import com.alignet.flotas.cliente.util.ConvertidorMsgReimpresion;
import com.alignet.flotas.cliente.util.ConvertidorMsgReporte;

public class ServicioMensaje {
	private GenericPackager empaquetado;
	private CanalAutorizadorAdquirente canal;

	public ServicioMensaje() {
		try {
			this.configurarEmpaquetado();
			this.configurarCanal();

		} catch (ISOException isoException) {
			isoException.printStackTrace();

		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public ISOMsg procesarMensajeAutorizacion(ISOMsg mensajeISO) {

		ISOMsg respuesta = null;

		try {
			this.canal.connect();

			mensajeISO.setPackager(this.empaquetado);

			this.canal.send(mensajeISO);
			respuesta = this.canal.receive();
			this.canal.disconnect();

		} catch (IOException ioException) {
			ioException.printStackTrace();

		} catch (ISOException isoException) {
			isoException.printStackTrace();
		}

		return respuesta;
	}

	public ISOMsg procesarMensajeAutorizacion(MensajeAutorizacion mensaje) {

		ISOMsg respuesta = null;

		try {
			this.canal.connect();

			ISOMsg mensajeAutorizacion = ConvertidorMsgAutorizacion.convertirMensajeAutorizacion(mensaje);
			mensajeAutorizacion.setPackager(this.empaquetado);

			this.canal.send(mensajeAutorizacion);
			respuesta = this.canal.receive();
			this.canal.disconnect();

		} catch (IOException ioException) {
			ioException.printStackTrace();

		} catch (ISOException isoException) {
			isoException.printStackTrace();
		}

		return respuesta;
	}

	public ISOMsg procesarMensajeReverso(MensajeAnulacion mensaje) {

		ISOMsg respuesta = null;

		try {
			this.canal.connect();
			mensaje.setCodigoFuncion("400");
			mensaje.setCodigoRazon("4006");
			ISOMsg mensajeExtorno = ConvertidorMsgAnulacion.convertirMensajeAnulacion(mensaje);
			mensajeExtorno.setPackager(this.empaquetado);

			this.canal.send(mensajeExtorno);
			respuesta = this.canal.receive();
			this.canal.disconnect();

		} catch (IOException ioException) {
			ioException.printStackTrace();

		} catch (ISOException isoException) {
			isoException.printStackTrace();
		}

		return respuesta;
	}

	public ISOMsg procesarMensajeAnulacion(MensajeAnulacion mensaje) {

		ISOMsg respuesta = null;

		try {
			this.canal.connect();
			mensaje.setCodigoFuncion("100");
			mensaje.setCodigoRazon("4001");
			ISOMsg mensajeExtorno = ConvertidorMsgAnulacion.convertirMensajeAnulacion(mensaje);
			mensajeExtorno.setPackager(this.empaquetado);

			this.canal.send(mensajeExtorno);
			respuesta = this.canal.receive();
			this.canal.disconnect();

		} catch (IOException ioException) {
			ioException.printStackTrace();

		} catch (ISOException isoException) {
			isoException.printStackTrace();
		}

		return respuesta;
	}

	public ISOMsg procesarMensajeReporte(ISOMsg mensajeISO) {

		ISOMsg respuesta = null;

		try {
			this.canal.connect();

			mensajeISO.setPackager(this.empaquetado);

			this.canal.send(mensajeISO);
			respuesta = this.canal.receive();
			this.canal.disconnect();

		} catch (IOException ioException) {
			ioException.printStackTrace();

		} catch (ISOException isoException) {
			isoException.printStackTrace();
		}

		return respuesta;
	}

	public ISOMsg procesarMensajeReporte(MensajeReporteTxs mensaje) {

		ISOMsg respuesta = null;

		try {
			this.canal.connect();
			mensaje.setCodigoFuncion("301");

			ISOMsg mensajeExtorno = ConvertidorMsgReporte.convertirMensajeReporte(mensaje);
			mensajeExtorno.setPackager(this.empaquetado);

			this.canal.send(mensajeExtorno);
			respuesta = this.canal.receive();
			this.canal.disconnect();

		} catch (IOException ioException) {
			ioException.printStackTrace();

		} catch (ISOException isoException) {
			isoException.printStackTrace();
		}

		return respuesta;
	}

	public ISOMsg procesarMensajeReimpresionCliente(MensajeReimpresion mensaje) {

		ISOMsg respuesta = null;

		try {
			this.canal.connect();
			mensaje.setCodigoFuncion("303");

			ISOMsg mensajeReimpresion = ConvertidorMsgReimpresion.convertirMensajeReimpresion(mensaje);
			mensajeReimpresion.setPackager(this.empaquetado);

			this.canal.send(mensajeReimpresion);
			respuesta = this.canal.receive();
			this.canal.disconnect();

		} catch (IOException ioException) {
			ioException.printStackTrace();

		} catch (ISOException isoException) {
			isoException.printStackTrace();
		}

		return respuesta;
	}

	public ISOMsg procesarMensajeReimpresionEstServicio(MensajeReimpresion mensaje) {

		ISOMsg respuesta = null;
		System.out.println(mensaje);
		try {
			this.canal.connect();
			mensaje.setCodigoFuncion("302");

			ISOMsg mensajeReimpresion = ConvertidorMsgReimpresion.convertirMensajeReimpresion(mensaje);
			mensajeReimpresion.setPackager(this.empaquetado);
			System.out.println("mensajeReimpresion " + mensajeReimpresion);
			this.canal.send(mensajeReimpresion);
			respuesta = this.canal.receive();
			this.canal.disconnect();

		} catch (IOException ioException) {
			ioException.printStackTrace();

		} catch (ISOException isoException) {
			isoException.printStackTrace();
		}

		return respuesta;
	}

	private void configurarEmpaquetado() throws ISOException {

		this.empaquetado = new GenericPackager("tramaNucleoVPOSAdv.xml");
	}

	private void configurarCanal() throws IOException {

		// final ResourceBundle r = ResourceBundle.getBundle("canal");

		String host = "localhost";
		int port = 12164;
		// int timeout = Integer.parseInt(r.getString("canal.timeout"));
		int timeout = 30000;

		this.canal = new CanalAutorizadorAdquirente(host, port, this.empaquetado);
		this.canal.setTimeout(timeout);

		/**
		 * Logger logger = new Logger();
		 * logger.addListener(new SimpleLogListener(System.out));
		 * this.canal.setLogger(logger, "consoleLogger");
		 **/
	}
}
