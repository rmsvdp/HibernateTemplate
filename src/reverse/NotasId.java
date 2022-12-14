package reverse;
// Generated 7 nov 2022 21:01:25 by Hibernate Tools 6.1.3.Final

/**
 * NotasId generated by hbm2java
 */
public class NotasId implements java.io.Serializable {

	private int cdn;
	private String idn;

	public NotasId() {
	}

	public NotasId(int cdn, String idn) {
		this.cdn = cdn;
		this.idn = idn;
	}

	public int getCdn() {
		return this.cdn;
	}

	public void setCdn(int cdn) {
		this.cdn = cdn;
	}

	public String getIdn() {
		return this.idn;
	}

	public void setIdn(String idn) {
		this.idn = idn;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof NotasId))
			return false;
		NotasId castOther = (NotasId) other;

		return (this.getCdn() == castOther.getCdn()) && ((this.getIdn() == castOther.getIdn())
				|| (this.getIdn() != null && castOther.getIdn() != null && this.getIdn().equals(castOther.getIdn())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCdn();
		result = 37 * result + (getIdn() == null ? 0 : this.getIdn().hashCode());
		return result;
	}

}
