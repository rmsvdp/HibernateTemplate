package reverse;
// Generated 7 nov 2022 21:01:25 by Hibernate Tools 6.1.3.Final

/**
 * Notas generated by hbm2java
 */
public class Notas implements java.io.Serializable {

	private NotasId id;
	private Alumnos alumnos;
	private Asignaturas asignaturas;
	private Integer nota;

	public Notas() {
	}

	public Notas(NotasId id, Alumnos alumnos, Asignaturas asignaturas) {
		this.id = id;
		this.alumnos = alumnos;
		this.asignaturas = asignaturas;
	}

	public Notas(NotasId id, Alumnos alumnos, Asignaturas asignaturas, Integer nota) {
		this.id = id;
		this.alumnos = alumnos;
		this.asignaturas = asignaturas;
		this.nota = nota;
	}

	public NotasId getId() {
		return this.id;
	}

	public void setId(NotasId id) {
		this.id = id;
	}

	public Alumnos getAlumnos() {
		return this.alumnos;
	}

	public void setAlumnos(Alumnos alumnos) {
		this.alumnos = alumnos;
	}

	public Asignaturas getAsignaturas() {
		return this.asignaturas;
	}

	public void setAsignaturas(Asignaturas asignaturas) {
		this.asignaturas = asignaturas;
	}

	public Integer getNota() {
		return this.nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

}
