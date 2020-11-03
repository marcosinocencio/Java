package aplicacao.pkg1;
import java.io.Serializable;
/**
 *
 * @author FELIPE
 */
public class DatagramaIP implements Serializable{
        private long tamanho;
	private int t_cabecalho, fragmentoffset;
	private short fragflag;
	private String protocolo, IPorig, IPdest;
	byte[] data;
	
	public long gettamanho() {
		return tamanho;
	}
	public void settamanho(long length) {
		this.tamanho = length;
	}
	public int gett_cabecalho() {
		return t_cabecalho;
	}
	public void sett_cabecalho(int headlen) {
		this.t_cabecalho = headlen;
	}
	public short getFragflag() {
		return fragflag;
	}
	public void setFragflag(short fragflag) {
		this.fragflag = fragflag;
	}
	public int getFragmentoffset() {
		return fragmentoffset;
	}
	public void setFragmentoffset(int fragmentoffset) {
		this.fragmentoffset = fragmentoffset;
	}
	public String getprotocolo() {
		return protocolo;
	}
	public void setprotocolo(String protocol) {
		this.protocolo = protocol;
	}
	public String getIPorig() {
		return IPorig;
	}
	public void setIPorig(String ipOrig) {
		this.IPorig = ipOrig;
	}
	public String getIPdest() {
		return IPdest;
	}
	public void setIPdest(String ipDest) {
		this.IPdest = ipDest;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}

    void setData(DatagramaIP datagram) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
