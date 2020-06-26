package br.ufc.russas.n2s.darwin.beans;

import br.ufc.russas.n2s.darwin.model.Arquivo;
import br.ufc.russas.n2s.darwin.model.Documentacao;
import br.ufc.russas.n2s.darwin.model.Participante;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Lavínia Matoso
 */
public class DocumentacaoBeans implements Beans, Serializable {

	private static final long serialVersionUID = 484935922477100156L;
	
	private long codDocumentacao;
    private ParticipanteBeans candidato;
    private List<ArquivoBeans> documentos;

    public long getCodDocumentacao() {
        return codDocumentacao;
    }

    public void setCodDocumentacao(long codDocumentacao) {
        this.codDocumentacao = codDocumentacao;
    }

    public ParticipanteBeans getCandidato() {
        return candidato;
    }

    public void setCandidato(ParticipanteBeans candidato) {
        this.candidato = candidato;
    }

    public List<ArquivoBeans> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<ArquivoBeans> documentos) {
        this.documentos = documentos;
    }

    @Override
    public Object toBusiness() {
        Documentacao documentacao = new Documentacao();
        if (this.getCodDocumentacao() > 0) {
            documentacao.setCodDocumentacao(this.getCodDocumentacao());
        }
        Participante p = (Participante) this.getCandidato().toBusiness();
        documentacao.setCandidato(p);
        ArrayList<Arquivo> arquivos = new ArrayList<>();
        List<Arquivo> syncDocs;
        syncDocs = Collections.synchronizedList(arquivos);
        for (ArquivoBeans documento : this.getDocumentos()) {
            syncDocs.add((Arquivo) documento.toBusiness());
        }
        documentacao.setDocumentos(syncDocs);
        return documentacao;
    }

    @Override
    public Beans toBeans(Object object) {
        if (object != null) {
            if (object instanceof Documentacao) {
                Documentacao documentacao = (Documentacao) object;
                this.setCodDocumentacao(this.getCodDocumentacao());
                ParticipanteBeans participanteBeans = new ParticipanteBeans();
                participanteBeans.toBeans(documentacao.getCandidato());
                this.setCandidato(participanteBeans);
                ArrayList<ArquivoBeans> docs = new ArrayList<>();
                List<ArquivoBeans> syncDocs;
                syncDocs = Collections.synchronizedList(docs);
                for (Arquivo documento : documentacao.getDocumentos()) {
                    ArquivoBeans a = new ArquivoBeans();
                    a.toBeans(documento);
                    syncDocs.add(a);
                }
                this.setDocumentos(syncDocs);
                return this;
            } else {
                throw new IllegalArgumentException("Isso não é uma Documentação!");
            }
        } else {
            throw new NullPointerException("Documentação não pode ser nula!");
        }
    }

}
