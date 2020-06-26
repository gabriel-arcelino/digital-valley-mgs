package br.ufc.russas.n2s.darwin.model;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Wallison Carlos
 */
@Entity
@Table(name = "arquivo")
@Converter(autoApply = true)

public class Arquivo implements AttributeConverter<LocalDateTime, Timestamp>, Serializable {
    
	private static final long serialVersionUID = 3401820503669532090L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codArquivo")
    private long codArquivo;
	
    private String titulo;
    
    @Column(name = "arquivo")
    private File arquivo;
    
    @Column(name = "data")
    private LocalDateTime data;

    public Arquivo() {
    }

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
        return (attribute == null) ? null : Timestamp.valueOf(attribute);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
        return (dbData == null) ? null : dbData.toLocalDateTime();
    }

    public long getCodArquivo() {
        return codArquivo;
    }

    public void setCodArquivo(long codArquivo) {
        if (codArquivo > 0) {
            this.codArquivo = codArquivo;
        } else {
            //throw new IllegalCodeException("Código de arquivo deve ser maior que zero!");
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null) {
            throw new NullPointerException("Titulo de arquivo não pode ser nulo!");
        } else if (titulo.isEmpty()) {
            throw new NullPointerException("Titulo de arquivo não pode ser vazio!");
        } else {
            this.titulo = titulo;
        }
    }

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        if (arquivo != null) {
            this.arquivo = arquivo;
        } else {
            throw new NullPointerException("Arquivo não pode ser nulo!");
        }
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        if (data != null) {
            this.data = data;
        } else {
            throw new NullPointerException("Data não pode ser nula!");
        }
    }
}
