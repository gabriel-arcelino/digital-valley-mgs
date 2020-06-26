package br.ufc.russas.n2s.darwin.controller;

import br.ufc.russas.n2s.darwin.beans.DocumentacaoBeans;
import br.ufc.russas.n2s.darwin.beans.EtapaBeans;
import br.ufc.russas.n2s.darwin.beans.ParticipanteBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.model.Arquivo;
import br.ufc.russas.n2s.darwin.model.Documentacao;
import br.ufc.russas.n2s.darwin.model.Email;
import br.ufc.russas.n2s.darwin.model.Log;
import br.ufc.russas.n2s.darwin.model.Participante;
import br.ufc.russas.n2s.darwin.model.Selecao;
import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;
import br.ufc.russas.n2s.darwin.service.EtapaServiceIfc;
import br.ufc.russas.n2s.darwin.service.LogServiceIfc;
import br.ufc.russas.n2s.darwin.service.ParticipanteServiceIfc;
import br.ufc.russas.n2s.darwin.service.SelecaoServiceIfc;
import br.ufc.russas.n2s.darwin.service.UsuarioServiceIfc;
import util.Constantes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author Wallison Carlos
 */
@Controller("participarEtapaController")
@RequestMapping("/participarEtapa")
public class ParticiparEtapaController {

	private EtapaServiceIfc etapaServiceIfc;
	private SelecaoServiceIfc selecaoServiceIfc;
	private UsuarioServiceIfc usuarioServiceIfc;
	private ParticipanteServiceIfc participanteServiceIfc;
	private LogServiceIfc logServiceIfc;

	public EtapaServiceIfc getEtapaServiceIfc() {
		return etapaServiceIfc;
	}

	public UsuarioServiceIfc getUsuarioServiceIfc() {
		return usuarioServiceIfc;
	}

	@Autowired(required = true)
	public void setUsuarioServiceIfc(@Qualifier("usuarioServiceIfc") UsuarioServiceIfc usuarioServiceIfc) {
		this.usuarioServiceIfc = usuarioServiceIfc;
	}

	@Autowired(required = true)
	public void setEtapaServiceIfc(@Qualifier("etapaServiceIfc") EtapaServiceIfc etapaServiceIfc) {
		this.etapaServiceIfc = etapaServiceIfc;
	}

	public SelecaoServiceIfc getSelecaoServiceIfc() {
		return selecaoServiceIfc;
	}

	@Autowired(required = true)
	public void setSelecaoServiceIfc(@Qualifier("selecaoServiceIfc") SelecaoServiceIfc selecaoServiceIfc) {
		this.selecaoServiceIfc = selecaoServiceIfc;
	}

	public ParticipanteServiceIfc getParticipanteServiceIfc() {
		return participanteServiceIfc;
	}

	@Autowired(required = true)
	public void setParticipanteServiceIfc(
			@Qualifier("participanteServiceIfc") ParticipanteServiceIfc participanteServiceIfc) {
		this.participanteServiceIfc = participanteServiceIfc;
	}

	public LogServiceIfc getLogServiceIfc() {
		return logServiceIfc;
	}

	@Autowired
	public void setLogServiceIfc(@Qualifier("logServiceIfc") LogServiceIfc logServiceIfc) {
		this.logServiceIfc = logServiceIfc;
	}

	@RequestMapping(value = "/{codEtapa}", method = RequestMethod.GET)
	public String getIndex(@PathVariable long codEtapa, Model model, HttpServletRequest request) {
		EtapaBeans etapaBeans = this.etapaServiceIfc.getEtapa(codEtapa);
		model.addAttribute("etapa", etapaBeans);
		return "participar-etapa";
	}

	@RequestMapping(value = "/inscricao/{codEtapa}", method = RequestMethod.GET)
	public String getIndexInscricao(@PathVariable long codEtapa, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		boolean isParticipante = false;
		UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
		EtapaBeans etapaBeans = this.etapaServiceIfc.getEtapa(codEtapa);
		List<ParticipanteBeans> lista = etapaBeans.getParticipantes();
		for (ParticipanteBeans pb : lista) {
			if (pb.getCandidato().getCodUsuario() == usuario.getCodUsuario()) {
				isParticipante = true;
				break;
			}
		}
		SelecaoBeans selecao = (SelecaoBeans) session.getAttribute("selecao");
		this.getLogServiceIfc().adicionaLog(
				new Log(LocalDate.now(), (UsuarioDarwin) usuario.toBusiness(), (Selecao) selecao.toBusiness(),
						"O(A) usuario(a) " + usuario.getNome() + " portador do CPF " + usuario.getCPF()
								+ " entrou na pagina de inscrição na seleção (" + selecao.getCodSelecao() + ") "
								+ selecao.getTitulo() + " em " + LocalDate.now() + "."));
		model.addAttribute("isParticipante", isParticipante);
		model.addAttribute("etapa", etapaBeans);
		return "participar-etapa";
	}

	@RequestMapping(value = "/inscricao/{codEtapa}", method = RequestMethod.POST)
	public String participa(@PathVariable long codEtapa, HttpServletRequest request, Model model,
			MultipartHttpServletRequest r, HttpServletResponse response,
			@RequestParam("arquivos") List<MultipartFile> documentos) throws IOException {
		HttpSession session = request.getSession();
		EtapaBeans inscricao = null;
		try {

			synchronized (getClass()) {
				inscricao = this.etapaServiceIfc.getEtapa(codEtapa);
				SelecaoBeans selecao = this.etapaServiceIfc.getSelecao(inscricao);
				UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");

				boolean isAvaliador = false;
				for (EtapaBeans etapa : selecao.getEtapas()) {
					for (UsuarioBeans avaliador : etapa.getAvaliadores()) {
						if (avaliador.getCodUsuario() == usuario.getCodUsuario()) {
							isAvaliador = true;
							break;
						}
					}
					if (isAvaliador)
						break;
				}
				if (!isAvaliador) {
					if (this.etapaServiceIfc.isParticipante(inscricao, usuario))
						throw new Exception("Você já está inscrito nessa seleção.");
					File dir = new File(Constantes.getDocumentsDir() + File.separator + "Selecao_"
							+ selecao.getCodSelecao() + File.separator + "Etapa_" + inscricao.getCodEtapa()
							+ File.separator + "Candidato_" + usuario.getCodUsuario() + File.separator);
					if (!dir.exists()) {
						dir.mkdir();
					}
					this.etapaServiceIfc.setUsuario(usuario);
					List<Arquivo> arquivos = new ArrayList<Arquivo>();
					String[] nomeDocumento = request.getParameterValues("nomeDocumento");
					for (int i = 0; i < documentos.size(); i++) {
						if (i < nomeDocumento.length) {
							String nome = nomeDocumento[i];
							MultipartFile file = documentos.get(i);
							if (file != null) {
								if(file.getSize() <= Long.parseLong(Constantes.getSizeFile())) {
									Arquivo documento = new Arquivo();
									String aux = file.getOriginalFilename();
									if (aux.length() > 4) {
										String expressao = aux.substring(aux.lastIndexOf("."), aux.length());
										if (!expressao.equals(".pdf")) {
											throw new IllegalArgumentException("Formato de arquivo enviado não é .pdf");
										}
										java.io.File convFile = java.io.File.createTempFile(file.getOriginalFilename(),
												".pdf", dir);
										FileOutputStream fos = new FileOutputStream(convFile);
										fos.write(file.getBytes());
										fos.close();
										documento.setTitulo(nome);
										documento.setData(LocalDateTime.now());
										documento.setArquivo(convFile);
										arquivos.add(documento);
									}
								} else {
									throw new Exception("O tamanho do arquivo "+ file.getOriginalFilename()+" excede o permitido de "+ Math.floor(Long.parseLong(Constantes.getSizeFile())/(1024*1024)) +"MB.");
								}
							}
						}
					}

					Documentacao documentacao = new Documentacao();
					Participante participante = new Participante();
					participante.setCandidato((UsuarioDarwin) usuario.toBusiness());
					participante.setData(LocalDateTime.now());
					documentacao.setCandidato(participante);
					documentacao.setDocumentos(arquivos);

					if (arquivos.size() > 0) {
						etapaServiceIfc.participa(inscricao,
								(ParticipanteBeans) new ParticipanteBeans().toBeans(participante),
								(DocumentacaoBeans) new DocumentacaoBeans().toBeans(documentacao));

					} else {
						etapaServiceIfc.participa(inscricao,
								(ParticipanteBeans) new ParticipanteBeans().toBeans(participante));
					}
					this.getLogServiceIfc()
							.adicionaLog(new Log(LocalDate.now(), (UsuarioDarwin) usuario.toBusiness(),
									(Selecao) selecao.toBusiness(),
									"O(A) usuario(a) " + usuario.getNome() + " portador do CPF " + usuario.getCPF()
											+ " realizou a inscrição na seleção (" + selecao.getCodSelecao() + ") "
											+ selecao.getTitulo() + " em " + LocalDate.now() + "."));
					session.setAttribute("mensagem",
							"Agora você está inscrito na seleção ".concat(selecao.getTitulo()));
					List<SelecaoBeans> selecoes = this.getSelecaoServiceIfc().listaSelecoesAssociada(usuario);
					Thread sendEmail = new Thread(new Email(usuario, "Inscrição em seleção!", "Inscrição em seleção",
							"Sua inscrição na <b>Seleção " + selecao.getTitulo() + "</b> foi realizada com sucesso!"));
					sendEmail.start();
					HashMap<SelecaoBeans, EtapaBeans> etapasAtuais = new HashMap<>();
					for (SelecaoBeans s : selecoes) {
						etapasAtuais.put(s, this.getSelecaoServiceIfc().getEtapaAtual(s));
					}

					model.addAttribute("selecoes", selecoes);
					model.addAttribute("etapasAtuais", etapasAtuais);
					session.setAttribute("status", "success");
					return "redirect:/minhas_Selecoes";
				} else {
					throw new Exception(
							"Você é avaliador de alguma das etapas desta seleção, por isso não é possível se inscrever!");
				}

			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "danger");
			return "redirect:/participarEtapa/inscricao/" + inscricao.getCodEtapa();
		} catch (IllegalArgumentException | NullPointerException | IllegalAccessException e) {
			e.printStackTrace();
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "warning");
			return "redirect:/participarEtapa/inscricao/" + inscricao.getCodEtapa();
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "danger");
			return "redirect:/participarEtapa/inscricao/" + inscricao.getCodEtapa();
		}
	}

	@RequestMapping(value = "/{codEtapa}", method = RequestMethod.POST)
	public @ResponseBody void anexaDocumentacao(@PathVariable long codEtapa, HttpServletRequest request,
			HttpServletResponse response, @RequestParam("nomeDocumento") String[] nomeDocumento,
			@RequestParam("arquivos") MultipartFile documentos[]) throws IOException {
		HttpSession session = request.getSession();
		EtapaBeans etapa = null;
		try {
			etapa = this.etapaServiceIfc.getEtapa(codEtapa);
			SelecaoBeans selecao = this.etapaServiceIfc.getSelecao(etapa);
			UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
			File dir = new File(Constantes.getDocumentsDir() + File.separator + "Selecao_" + selecao.getCodSelecao()
					+ File.separator + "Etapa_" + etapa.getCodEtapa() + File.separator + "Candidato_"
					+ usuario.getCodUsuario() + File.separator);
			dir.mkdir();
			this.etapaServiceIfc.setUsuario(usuario);
			List<Arquivo> arquivos = Collections.synchronizedList(new ArrayList<Arquivo>());
			for (int i = 0; i < documentos.length; i++) {
				String nome = nomeDocumento[i];
				MultipartFile file = documentos[i];
				if (!file.isEmpty()) {
					Arquivo documento = new Arquivo();
					String aux = file.getOriginalFilename();
					String expressao = aux.substring(aux.lastIndexOf("."), aux.length());
					if (!expressao.equals(".pdf")) {
						throw new IllegalArgumentException("Formato de arquivo enviado não é .pdf");
					}
					java.io.File convFile = java.io.File.createTempFile(file.getOriginalFilename(), ".pdf", dir);
					FileOutputStream fos = new FileOutputStream(convFile);
					fos.write(file.getBytes());
					fos.close();
					documento.setTitulo(nome);
					documento.setData(LocalDateTime.now());
					documento.setArquivo(convFile);
					arquivos.add(documento);
				}
			}
			Documentacao documentacao = new Documentacao();

			Participante participante = null;
			List<ParticipanteBeans> partis = etapa.getParticipantes().stream()
					.filter(parti -> parti.getCandidato().getCodUsuario() == usuario.getCodUsuario())
					.collect(Collectors.toList());

			if (partis.size() != 0) {
				participante = (Participante) partis.get(0).toBusiness();
			} else {
				throw new Exception("Participante não encontrado");
			}
			System.out.println(participante.getCandidato().getNome());
			participante.setCandidato((UsuarioDarwin) usuario.toBusiness());
			participante.setData(LocalDateTime.now());
			documentacao.setCandidato(participante);
			documentacao.setDocumentos(arquivos);
			this.etapaServiceIfc.anexaDocumentacao(etapa,
					(ParticipanteBeans) new ParticipanteBeans().toBeans(participante),
					(DocumentacaoBeans) new DocumentacaoBeans().toBeans(documentacao));
			Thread sendEmail = new Thread(new Email(usuario, "Inscrição em seleção!", "Inscrição em seleção",
					"Sua inscrição na <b>Seleção " + selecao.getTitulo() + "</b> foi realizada com sucesso!"));
			sendEmail.start();
			session.setAttribute("mensagem", "Agora você está inscrito na etapa ".concat(etapa.getTitulo()));
			session.setAttribute("status", "success");
			response.sendRedirect(Constantes.getAppUrl() + "/minhas_Selecoes");
		} catch (MultipartException e) {
			session.setAttribute("mensagem", "O tamanho dos arquivos ultrapassa a capacidade de upload.");
			session.setAttribute("status", "danger");
			response.sendRedirect(Constantes.getAppUrl() + "/participarEtapa/" + etapa.getCodEtapa());
		} catch (NumberFormatException e) {
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "danger");
			response.sendRedirect(Constantes.getAppUrl() + "/participarEtapa/" + etapa.getCodEtapa());
		} catch (IllegalArgumentException | NullPointerException | IllegalAccessException e) {
			e.printStackTrace();
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "danger");
			response.sendRedirect(Constantes.getAppUrl() + "/participarEtapa/" + etapa.getCodEtapa());
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "danger");
			response.sendRedirect(Constantes.getAppUrl() + "/participarEtapa/" + etapa.getCodEtapa());
		}
	}

}
