import java.io.Serializable;


public class DatagramaIP implements Serializable
{
	private long length;
	private int headlen;
	private short fragflag;
	private int fragmentoffset;
	private String protocol;
	private String ipOrig, ipDest;
	byte[] data;
	
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public int getHeadlen() {
		return headlen;
	}
	public void setHeadlen(int headlen) {
		this.headlen = headlen;
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
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getIpOrig() {
		return ipOrig;
	}
	public void setIpOrig(String ipOrig) {
		this.ipOrig = ipOrig;
	}
	public String getIpDest() {
		return ipDest;
	}
	public void setIpDest(String ipDest) {
		this.ipDest = ipDest;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
}
