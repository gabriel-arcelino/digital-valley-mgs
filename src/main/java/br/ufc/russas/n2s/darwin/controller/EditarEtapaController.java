package br.ufc.russas.n2s.darwin.controller;

import br.ufc.russas.n2s.darwin.beans.EtapaBeans;
import br.ufc.russas.n2s.darwin.beans.ParticipanteBeans;
import br.ufc.russas.n2s.darwin.beans.PeriodoBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.model.Email;
import br.ufc.russas.n2s.darwin.model.EnumCriterioDeAvaliacao;
import br.ufc.russas.n2s.darwin.model.EnumEstadoEtapa;
import br.ufc.russas.n2s.darwin.model.EnumPermissao;
import br.ufc.russas.n2s.darwin.model.Log;
import br.ufc.russas.n2s.darwin.model.Periodo;
import br.ufc.russas.n2s.darwin.model.Recurso;
import br.ufc.russas.n2s.darwin.model.Selecao;
import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;
import br.ufc.russas.n2s.darwin.model.exception.IllegalCodeException;
import br.ufc.russas.n2s.darwin.service.EtapaServiceIfc;
import br.ufc.russas.n2s.darwin.service.LogServiceIfc;
import br.ufc.russas.n2s.darwin.service.SelecaoServiceIfc;
import br.ufc.russas.n2s.darwin.service.UsuarioServiceIfc;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

/**
 *
 * @author Gilberto
 */
@Controller("editarEtapaController")
@RequestMapping("/editarEtapa")
public class EditarEtapaController {

	private EtapaServiceIfc etapaServiceIfc;
	private SelecaoServiceIfc selecaoServiceIfc;
	private UsuarioServiceIfc usuarioServiceIfc;
	private LogServiceIfc logServiceIfc;

	public EtapaServiceIfc getEtapaServiceIfc() {
		return etapaServiceIfc;
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

	public UsuarioServiceIfc getUsuarioServiceIfc() {
		return usuarioServiceIfc;
	}

	@Autowired(required = true)
	public void setUsuarioServiceIfc(@Qualifier("usuarioServiceIfc") UsuarioServiceIfc usuarioServiceIfc) {
		this.usuarioServiceIfc = usuarioServiceIfc;
	}

	public LogServiceIfc getLogServiceIfc() {
		return logServiceIfc;
	}

	@Autowired
	public void setLogServiceIfc(@Qualifier("logServiceIfc") LogServiceIfc logServiceIfc) {
		this.logServiceIfc = logServiceIfc;
	}

	@RequestMapping(value = "/{codSelecao}/{codEtapa}", method = RequestMethod.GET)
	public String getIndex(@PathVariable long codSelecao, @PathVariable long codEtapa, Model model,
			HttpServletRequest request) {
		EtapaBeans etapaBeans = this.etapaServiceIfc.getEtapa(codEtapa);
		SelecaoBeans selecao = selecaoServiceIfc.getSelecao(codSelecao);
		UsuarioBeans usuario = (UsuarioBeans) request.getSession().getAttribute("usuarioDarwin");
		if (selecao.getResponsaveis().contains(usuario)
				|| usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
			List<UsuarioBeans> usuarios = this.getUsuarioServiceIfc()
					.listaUsuariosComPermissao(EnumPermissao.AVALIADOR);
			if (etapaBeans.getCodEtapa() == selecao.getInscricao().getCodEtapa()) {
				model.addAttribute("tipo", "inscricao");
			} else {
				model.addAttribute("tipo", "etapa");
			}

			List<Long> codAvaliadores = Collections.synchronizedList(new ArrayList<>());
			List<String> nomeAvaliadores = Collections.synchronizedList(new ArrayList<>());

			for (UsuarioBeans avaliador : etapaBeans.getAvaliadores()) {
				codAvaliadores.add(avaliador.getCodUsuario());
				nomeAvaliadores.add(avaliador.getNome());
			}
			String json = new Gson().toJson(nomeAvaliadores);
			model.addAttribute("selecao", selecao);
			model.addAttribute("etapa", etapaBeans);
			model.addAttribute("codAvaliadores", codAvaliadores);
			model.addAttribute("nomeAvaliadores", json);
			model.addAttribute("listaDocumentos", new Gson().toJson(etapaBeans.getDocumentacaoExigida()));
			model.addAttribute("listaNumeroDocumentos", etapaBeans.getDocumentacaoExigida().size());
			model.addAttribute("listaDocumentosOp", new Gson().toJson(etapaBeans.getDocumentacaoOpcional()));
			model.addAttribute("listaNumeroDocumentosOp", etapaBeans.getDocumentacaoOpcional().size());
			model.addAttribute("usuarios", usuarios);
			return "editar-etapa";
		} else {
			return "error/404";
		}
	}

	private boolean editarEtapa(UsuarioBeans usuario, HttpSession session, Long codEtapa, long codSelecao,
			SelecaoBeans selecao, EtapaBeans etapa, EtapaBeans etapaBeans, PeriodoBeans novoPeriodo,
			HttpServletRequest request) throws Exception {

		etapaBeans.setPeriodo(novoPeriodo);

		if (etapaBeans.getEstado() == EnumEstadoEtapa.ESPERA) {
			String[] codAvaliadores = request.getParameterValues("codAvaliadores");
			String[] documentosExigidos = request.getParameterValues("documentosExigidos");
			String[] documentosOpcionais = request.getParameterValues("documentosOpcionais");
			int criterio = Integer.parseInt(request.getParameter("criterio"));

			if (criterio == 1) {
				etapaBeans.setCriterioDeAvaliacao(EnumCriterioDeAvaliacao.NOTA);
				etapaBeans.setNotaMinima(etapa.getNotaMinima());
			} else if (criterio == 2) {
				etapaBeans.setCriterioDeAvaliacao(EnumCriterioDeAvaliacao.APROVACAO);
			} else if (criterio == 3) {
				etapaBeans.setCriterioDeAvaliacao(EnumCriterioDeAvaliacao.DEFERIMENTO);
			}

			etapaBeans.setTitulo(etapa.getTitulo());
			etapaBeans.setDescricao(etapa.getDescricao());
			long codPrerequisito = Long.parseLong(request.getParameter("prereq"));
			EtapaBeans pre = this.etapaServiceIfc.getEtapa(codPrerequisito);
			etapaBeans.setPrerequisito(pre);

			ArrayList<UsuarioBeans> avaliadores = new ArrayList<>();
			if (codAvaliadores != null) {
				for (String cod : codAvaliadores) {
					UsuarioBeans u = this.getUsuarioServiceIfc().getUsuario(Long.parseLong(cod), 0);
					if (u != null) {
						avaliadores.add(u);
					}
				}
			}

			if (avaliadores.size() > 1 && (etapaBeans.getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.APROVACAO
					|| etapaBeans.getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.DEFERIMENTO)) {
				throw new IllegalArgumentException(
						"Esta etapa só pode ter um avaliador devido ao critério de avaliação escolhido.");
			}

			if (documentosExigidos != null) {
				ArrayList<String> docs = new ArrayList<>();
				for (String documento : documentosExigidos) {
					docs.add(documento);
				}
				etapaBeans.setDocumentacaoExigida(docs);
			} else {
				etapaBeans.setDocumentacaoExigida(new ArrayList<>());
			}

			if (documentosOpcionais != null) {
				ArrayList<String> docsOp = new ArrayList<>();
				for (String documento : documentosOpcionais) {
					docsOp.add(documento);
				}
				etapaBeans.setDocumentacaoOpcional(docsOp);
			} else {
				etapaBeans.setDocumentacaoOpcional(new ArrayList<>());
			}

			etapaBeans.setAvaliadores(avaliadores);
		}

		if (request.getParameter("dataInicioRecurso") != null
				&& (request.getParameter("dataInicioRecurso").length() >= 8)
				&& request.getParameter("dataTerminoRecurso") != null
				&& (request.getParameter("dataTerminoRecurso").length() >= 8)) {
			Recurso recurso = new Recurso();
			DateTimeFormatter formatte = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			PeriodoBeans pb = new PeriodoBeans(0, LocalDate.parse(request.getParameter("dataInicioRecurso"), formatte),
					LocalDate.parse(request.getParameter("dataTerminoRecurso"), formatte));

			if (!pb.getInicio().isAfter(etapaBeans.getPeriodo().getTermino())) {
				throw new Exception("O início do período de recurso não pode ser antes do termino da própria etapa!");
			}

			recurso.setPeriodo((Periodo) pb.toBusiness());
			etapaBeans.setRecurso(recurso);
		} else {
			etapaBeans.setRecurso(null);
		}

		List<EtapaBeans> subsequentes = selecao.getEtapas();
		Periodo novoP = (Periodo) etapaBeans.getPeriodo().toBusiness();
		for (EtapaBeans sub : subsequentes) {
			if (sub.getPrerequisito() != null && sub.getPrerequisito().getCodEtapa() == codEtapa) {
				if (etapa.getRecurso() != null && !etapaBeans.getRecurso().getPeriodo().getTermino().isBefore(sub.getPeriodo().getInicio())) {
					throw new Exception(
							"O termino do período de recurso não pode ser depois do início da próxima etapa!");
				}
			}
			if (sub.getCodEtapa() != etapa.getCodEtapa()) {
				Periodo periodo = (Periodo) sub.getPeriodo().toBusiness();
				if (periodo.isColide(novoP)) {
					session.setAttribute("status", "warning");
					session.setAttribute("mensagem", "As datas da etapa " + etapaBeans.getTitulo()
							+ " estão colidindo com as datas da etapa " + sub.getTitulo() + "!");
					session.setAttribute("selecao", selecao);
					return false;
				}
			}
		}

		this.getEtapaServiceIfc().atualizaEtapa(etapaBeans);
		selecao = this.getSelecaoServiceIfc().getSelecao(selecao.getCodSelecao());
		this.getLogServiceIfc().adicionaLog(new Log(LocalDate.now(), (UsuarioDarwin) usuario.toBusiness(),
				(Selecao) selecao.toBusiness(), "O(A) usuario(a) " + usuario.getNome() + " modificou a etapa "
						+ etapa.getTitulo() + " na seleção " + selecao.getTitulo() + " em " + LocalDate.now() + "."));
		session.setAttribute("status", "success");
		session.setAttribute("mensagem", "Etapa atualizada com sucesso!");
		session.setAttribute("selecao", selecao);
		return true;
	}

	@RequestMapping(value = "/{codSelecao}/{codEtapa}", method = RequestMethod.POST)
	public String atualiza(@PathVariable long codSelecao, @PathVariable long codEtapa, EtapaBeans etapa,
			BindingResult result, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
			SelecaoBeans selecao = this.selecaoServiceIfc.getSelecao(codSelecao);

			EtapaBeans etapaBeans = this.etapaServiceIfc.getEtapa(codEtapa);
			if (usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)
					|| selecao.getResponsaveis().contains(usuario)) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				PeriodoBeans periodoJaCadastrado = etapaBeans.getPeriodo();
				PeriodoBeans novoPeriodo = new PeriodoBeans();

				novoPeriodo.setCodPeriodo(periodoJaCadastrado.getCodPeriodo());
				novoPeriodo.setInicio(LocalDate.parse(request.getParameter("dataInicio"), formatter));
				novoPeriodo.setTermino(LocalDate.parse(request.getParameter("dataTermino"), formatter));

				boolean editadaComSucesso = false;

				if (etapaBeans.getEstado().equals(EnumEstadoEtapa.ANDAMENTO)) {
					if (!novoPeriodo.getInicio().isEqual(etapaBeans.getPeriodo().getInicio())
							&& selecao.isDivulgada()) {
						session.setAttribute("status", "warning");
						session.setAttribute("mensagem", "A data de início da etapa " + etapaBeans.getTitulo()
								+ " não pode ser atualizada pois ela já está em andamento!");
						session.setAttribute("selecao", selecao);
					} else {
						EtapaBeans proximaEtapa = null;
						for (EtapaBeans e : selecao.getEtapas()) {
							if (e.getPrerequisito().getCodEtapa() == etapaBeans.getCodEtapa()) {
								proximaEtapa = e;
								break;
							}
						}

						if (proximaEtapa != null) {
							if (novoPeriodo.getTermino().isBefore(proximaEtapa.getPeriodo().getInicio())) {
								editadaComSucesso = editarEtapa(usuario, session, codEtapa, codSelecao, selecao, etapa,
										etapaBeans, novoPeriodo, request);
							} else {
								session.setAttribute("selecao", selecao);
								session.setAttribute("mensagem",
										"A etapa não pode atualizada pois a data de termino é depois da data de inicio da etapa "
												+ proximaEtapa.getTitulo() + "!");
								session.setAttribute("status", "warning");
							}
						} else {
							editadaComSucesso = editarEtapa(usuario, session, codEtapa, codSelecao, selecao, etapa,
									etapaBeans, novoPeriodo, request);
						}

					}
				} else {
					EtapaBeans proximaEtapa = null;
										
					for (EtapaBeans e : selecao.getEtapas()) {
						if (e.getPrerequisito().getCodEtapa() == etapaBeans.getCodEtapa()) {
							proximaEtapa = e;
							break;
						}
					}

					if (proximaEtapa != null) {
						if (novoPeriodo.getTermino().isBefore(proximaEtapa.getPeriodo().getInicio()) && novoPeriodo
								.getInicio().isAfter(etapaBeans.getPrerequisito().getPeriodo().getTermino())) {
							editadaComSucesso = editarEtapa(usuario, session, codEtapa, codSelecao, selecao, etapa,
									etapaBeans, novoPeriodo, request);
						} else {
							session.setAttribute("selecao", selecao);
							session.setAttribute("mensagem",
									"A etapa não pode atualizado pois a data de termino é depois da data de inicio da etapa "
											+ proximaEtapa.getTitulo() + "!");
							session.setAttribute("status", "warning");
						}
					} else {
						editadaComSucesso = editarEtapa(usuario, session, codEtapa, codSelecao, selecao, etapa,
								etapaBeans, novoPeriodo, request);
					}
				}
								
				if (editadaComSucesso) {
					this.getSelecaoServiceIfc().setUsuario(usuario);
					int index = 0;
					List<EtapaBeans> etapas = selecao.getEtapas();
					for (EtapaBeans etapaIterator : etapas) {
						if (etapaIterator.getCodEtapa() == etapaBeans.getCodEtapa()) {
							selecao.getEtapas().remove(etapaIterator);
							selecao.getEtapas().add(index, etapaBeans);
							break;
						}
						index++;
					}

					selecao = this.getSelecaoServiceIfc().atualizaSelecao(selecao);
					this.getLogServiceIfc()
							.adicionaLog(new Log(LocalDate.now(), (UsuarioDarwin) usuario.toBusiness(),
									(Selecao) selecao.toBusiness(),
									"O(A) usuario(a) " + usuario.getNome() + " modificou a etapa " + etapa.getTitulo()
											+ " na seleção " + selecao.getTitulo() + " em " + LocalDate.now() + "."));
					return "redirect:/selecao/" + selecao.getCodSelecao();
				}

				return "redirect:/editarEtapa/" + codSelecao + "/" + codEtapa;

			} else {
				session.setAttribute("selecao", selecao);
				session.setAttribute("mensagem", "Você não tem permissão para alterar esta etapa!");
				session.setAttribute("status", "warning");
				return "redirect:/editarEtapa/" + codSelecao + "/" + codEtapa;
			}

		} catch (IllegalAccessException e) {
			model.addAttribute("mensagem", e.getMessage());
			model.addAttribute("status", "danger");
			return "redirect:/editarEtapa/" + codSelecao + "/" + codEtapa;
		} catch (IllegalCodeException e) {
			session.setAttribute("mensagem",
					"Etapa não pode ser atualizada! Verifique o conflito entre periodos com outras etapas!");
			session.setAttribute("status", "warning");
			return "redirect:/editarEtapa/" + codSelecao + "/" + codEtapa;
		} catch (IllegalArgumentException e) {
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "danger");
			return "redirect:/editarEtapa/" + codSelecao + "/" + codEtapa;
		} catch (NullPointerException e) {
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "warning");
			return "redirect:/editarEtapa/" + codSelecao + "/" + codEtapa;
		} catch (Exception e) {
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "warning");
			return "redirect:/editarEtapa/" + codSelecao + "/" + codEtapa;
		}

	}

	@RequestMapping(value = "/{codSelecao}/inscricao/{codInscricao}", method = RequestMethod.POST)
	public String atualizaInscricao(@PathVariable long codSelecao, @PathVariable long codInscricao,
			EtapaBeans inscricao, BindingResult result, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {

			UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
			SelecaoBeans selecao = this.getSelecaoServiceIfc().getSelecao(codSelecao);

			EtapaBeans inscricaoBeans = this.getEtapaServiceIfc().getEtapa(codInscricao);
			if (usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)
					|| selecao.getResponsaveis().contains(usuario)) {

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				PeriodoBeans periodoJaCadastrado = inscricaoBeans.getPeriodo();
				PeriodoBeans novoPeriodo = new PeriodoBeans();

				novoPeriodo.setCodPeriodo(periodoJaCadastrado.getCodPeriodo());
				if (selecao.isDivulgada()) {
					novoPeriodo.setInicio(periodoJaCadastrado.getInicio());
				} else {
					novoPeriodo.setInicio(LocalDate.parse(request.getParameter("dataInicio"), formatter));
				}

				novoPeriodo.setTermino(LocalDate.parse(request.getParameter("dataTermino"), formatter));
				inscricaoBeans.setPeriodo(novoPeriodo);

				if (selecao.getEtapas().size() > 0 && selecao.getEtapas().get(0) != null
						&& (novoPeriodo.getTermino().isAfter(selecao.getEtapas().get(0).getPeriodo().getInicio())
								|| novoPeriodo.getTermino()
										.isEqual(selecao.getEtapas().get(0).getPeriodo().getInicio()))) {
					session.setAttribute("selecao", selecao);
					session.setAttribute("mensagem",
							"Etapa " + inscricaoBeans.getTitulo()
									+ " não pode ter data de termino igual ou após a data de início da "
									+ selecao.getEtapas().get(0).getTitulo() + ".");
					session.setAttribute("status", "warning");
					return "redirect:/selecao/" + selecao.getCodSelecao();
				} else {

					if (request.getParameter("dataInicioRecurso") != null
							&& (request.getParameter("dataInicioRecurso").length() >= 8)
							&& request.getParameter("dataTerminoRecurso") != null
							&& (request.getParameter("dataTerminoRecurso").length() >= 8)) {
						Recurso recurso = new Recurso();

						PeriodoBeans pb = new PeriodoBeans(0,
								LocalDate.parse(request.getParameter("dataInicioRecurso"), formatter),
								LocalDate.parse(request.getParameter("dataTerminoRecurso"), formatter));
						
						if (!pb.getInicio().isAfter(inscricaoBeans.getPeriodo().getTermino())) {
							throw new Exception("O início do período de recurso não pode ser antes do termino da própria etapa!");
						}
						
						recurso.setPeriodo((Periodo) pb.toBusiness());
						inscricaoBeans.setRecurso(recurso);
					} else {
						inscricaoBeans.setRecurso(null);
					}
					
					List<EtapaBeans> subsequentes = selecao.getEtapas();
					Periodo novoP = (Periodo) inscricaoBeans.getPeriodo().toBusiness();
					for (EtapaBeans sub : subsequentes) {
						if (sub.getPrerequisito() != null && sub.getPrerequisito().getCodEtapa() == codInscricao){
							if (inscricaoBeans.getRecurso() != null && !inscricaoBeans.getRecurso().getPeriodo().getTermino().isBefore(sub.getPeriodo().getInicio())) {
								throw new Exception(
										"O termino do período de recurso não pode ser depois do início da próxima etapa!");
							}
						}
						if (sub.getCodEtapa() != inscricao.getCodEtapa()) {
							Periodo periodo = (Periodo) sub.getPeriodo().toBusiness();
							
							if (periodo.isColide(novoP)) {
								throw new IllegalArgumentException("Periodo Inválido!");
							}
						}
					}
					
					if (!selecao.isDivulgada() || usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {

						if (inscricaoBeans.getEstado() == EnumEstadoEtapa.ESPERA) {
							String[] codAvaliadores = request.getParameterValues("codAvaliadores");
							String[] documentosExigidos = request.getParameterValues("documentosExigidos");
							String[] documentosOpcionais = request.getParameterValues("documentosOpcionais");
							inscricaoBeans.setTitulo(inscricao.getTitulo());
							inscricaoBeans.setDescricao(inscricao.getDescricao());

							ArrayList<UsuarioBeans> avaliadores = new ArrayList<>();
							try {
								if (codAvaliadores != null) {
									for (String cod : codAvaliadores) {
										if (cod.contains("-")) {
											cod = cod.substring(0, cod.indexOf("-"));
										}
										UsuarioBeans u = this.getUsuarioServiceIfc().getUsuario(Long.parseLong(cod), 0);
										if (u != null) {
											avaliadores.add(u);
										}
									}
									if (avaliadores.size() > 1 && (inscricaoBeans
											.getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.APROVACAO
											|| inscricaoBeans
													.getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.DEFERIMENTO)) {
										session.setAttribute("mensagem",
												"Esta etapa só pode ter um avaliador devido ao critério de avaliação escolhido.");
										session.setAttribute("status", "danger");
										return "redirect:/editarEtapa/" + codSelecao + "/" + codInscricao;
									}
								}
							} catch (NumberFormatException e) {
								session.setAttribute("mensagem", "Ocorreu um erro ao cadastrar avaliador(es)!");
								session.setAttribute("status", "danger");
								return "redirect:/editarEtapa/" + codSelecao + "/" + codInscricao;
							}
							if (documentosExigidos != null) {
								ArrayList<String> docs = new ArrayList<>();
								for (String documento : documentosExigidos) {
									docs.add(documento);
								}
								inscricaoBeans.setDocumentacaoExigida(docs);
							} else {
								inscricaoBeans.setDocumentacaoExigida(new ArrayList<>());
							}

							if (documentosOpcionais != null) {
								ArrayList<String> docsOp = new ArrayList<>();
								for (String documento : documentosOpcionais) {
									docsOp.add(documento);
								}
								inscricaoBeans.setDocumentacaoOpcional(docsOp);
							} else {
								inscricaoBeans.setDocumentacaoOpcional(new ArrayList<>());
							}
							inscricaoBeans.setAvaliadores(avaliadores);
						}

						this.getSelecaoServiceIfc().setUsuario(usuario);
						selecao.setInscricao(inscricaoBeans);
						selecao = this.getSelecaoServiceIfc().atualizaSelecao(selecao);
						this.getLogServiceIfc()
								.adicionaLog(new Log(LocalDate.now(), (UsuarioDarwin) usuario.toBusiness(),
										(Selecao) selecao.toBusiness(),
										"O(A) usuario(a) " + usuario.getNome() + " modificou a etapa "
												+ inscricaoBeans.getTitulo() + " na seleção " + selecao.getTitulo()
												+ " em " + LocalDate.now() + "."));
						session.setAttribute("selecao", selecao);
						session.setAttribute("mensagem",
								"Etapa " + inscricaoBeans.getTitulo() + " atualizada com sucesso!");
						session.setAttribute("status", "success");
						return "redirect:/selecao/" + selecao.getCodSelecao();
					}

					this.getSelecaoServiceIfc().setUsuario(usuario);
					selecao.setInscricao(inscricaoBeans);
					selecao = this.getSelecaoServiceIfc().atualizaSelecao(selecao);
					session.setAttribute("selecao", selecao);
					session.setAttribute("mensagem",
							"Etapa " + inscricaoBeans.getTitulo() + " atualizada com sucesso!");
					session.setAttribute("status", "success");
					return "redirect:/selecao/" + selecao.getCodSelecao();
				}

			} else {
				session.setAttribute("selecao", selecao);
				session.setAttribute("mensagem", "Etapa já foi iniciada, não é mais possível realizar edições!");
				session.setAttribute("status", "warning");
				return "redirect:/editarEtapa/" + selecao.getCodSelecao() + "/" + codInscricao;
			}
		} catch (IllegalArgumentException e) {
			session.setAttribute("mensagem",
					"Etapa não pode ser atualizada! Verifique o conflito entre periodos com outras etapas!");
			session.setAttribute("status", "warning");
			return "redirect:/editarEtapa/" + codSelecao + "/" + codInscricao;
		} catch (Exception e) {
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "danger");
			return "redirect:/editarEtapa/" + codSelecao + "/" + codInscricao;
		}

	}

	@RequestMapping(value = "/divulgarResultado/{codSelecao}/{codEtapa}", method = RequestMethod.GET)
	public String divulgaResultado(@PathVariable long codSelecao, @PathVariable long codEtapa, BindingResult result,
			Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
			SelecaoBeans selecao = selecaoServiceIfc.getSelecao(codSelecao);
			if (selecao.getResponsaveis().contains(usuario)
					|| usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
				EtapaBeans etapa = etapaServiceIfc.getEtapa(codEtapa);
				etapa.setDivulgaResultado(true);
				etapaServiceIfc.atualizaEtapa(etapa);
				this.getLogServiceIfc()
						.adicionaLog(new Log(LocalDate.now(), (UsuarioDarwin) usuario.toBusiness(),
								(Selecao) selecao.toBusiness(),
								"O(A) usuario(a) " + usuario.getNome() + " divulgou o resultado da etapa "
										+ etapa.getTitulo() + " na seleção " + selecao.getTitulo() + " em "
										+ LocalDate.now() + "."));
				session.setAttribute("selecao", selecao);
				session.setAttribute("etapa", etapa);
				session.setAttribute("resultado", etapaServiceIfc.getResultado(etapa));
				session.setAttribute("mensagem", "Etapa divulgada com sucesso!");
				session.setAttribute("status", "success");
				return "redirect:/selecao/" + selecao.getCodSelecao();
			} else {
				return "error/404";
			}
		} catch (IllegalCodeException e) {
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "danger");
			return "redirect:/selecao/" + codSelecao;
		}

	}

	@RequestMapping(value = "/divulgarResultadoInscricao/{codSelecao}/{codInscricao}", method = RequestMethod.GET)
	public String divulgaResultadoInscricao(@PathVariable long codSelecao, @PathVariable long codInscricao, Model model,
			HttpServletRequest request) throws MalformedURLException, EmailException {
		HttpSession session = request.getSession();
		try {

			UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
			SelecaoBeans selecao = selecaoServiceIfc.getSelecao(codSelecao);
			if (selecao.getResponsaveis().contains(usuario)
					|| usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
				EtapaBeans etapa = etapaServiceIfc.getEtapa(codInscricao);
				this.etapaServiceIfc.setUsuario(usuario);
				if (etapa.getAvaliacoes() == null) {
					throw new Exception("Não existem avaliações para serem divulgadas!");
				}
				etapa.setDivulgaResultado(true);
				etapa = etapaServiceIfc.atualizaEtapa(etapa);
				List<Thread> threadsEmail = Collections.synchronizedList(new ArrayList<Thread>());
				for (int i = 0; i < etapa.getParticipantes().size(); i++) {
					ParticipanteBeans p = etapa.getParticipantes().get(i);
					threadsEmail.add(new Thread(new Email(p.getCandidato(), "Resultado de etapa divulgado!",
							"Resultado de etapa divulgado", "O resultado da <b>Etapa de " + etapa.getTitulo()
									+ "</b> da <b>Seleção " + selecao.getTitulo() + "</b> foi divulgado!")));
					if (p.getCandidato().isRecebeEmail()) {
						threadsEmail.get(i).start();
					}
				}
				this.getLogServiceIfc()
						.adicionaLog(new Log(LocalDate.now(), (UsuarioDarwin) usuario.toBusiness(),
								(Selecao) selecao.toBusiness(),
								"O(A) usuario(a) " + usuario.getNome() + " divulgou o resultado da etapa "
										+ etapa.getTitulo() + " na seleção " + selecao.getTitulo() + " em "
										+ LocalDate.now() + "."));
				session.setAttribute("selecao", selecao);
				session.setAttribute("etapa", etapa);
				session.setAttribute("resultado", etapaServiceIfc.getResultado(etapa));
				session.setAttribute("mensagem", "Resultado da etapa divulgada com sucesso!");
				session.setAttribute("status", "success");
				return "redirect:/selecao/" + selecao.getCodSelecao();
			} else {
				return "error/404";
			}
		} catch (Exception e) {
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "danger");
			return "redirect:/selecao/" + codSelecao;
		}

	}

	@RequestMapping(value = "/removerEtapa/{codSelecao}/{codEtapa}", method = RequestMethod.GET)
	public String removerEtapa(@PathVariable long codSelecao, @PathVariable long codEtapa, Model model,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
			SelecaoBeans selecao = selecaoServiceIfc.getSelecao(codSelecao);
			if (selecao.getResponsaveis().contains(usuario)
					|| usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
				this.getSelecaoServiceIfc().setUsuario(usuario);
				EtapaBeans etapa = this.getEtapaServiceIfc().getEtapa(codEtapa);
				List<EtapaBeans> etapas = selecao.getEtapas();
				etapas.remove(etapa);
				selecao.setEtapas(etapas);
				selecao = this.getSelecaoServiceIfc().atualizaSelecao(selecao);
				this.getEtapaServiceIfc().removeEtapa(etapa);
				this.getLogServiceIfc()
						.adicionaLog(new Log(LocalDate.now(), (UsuarioDarwin) usuario.toBusiness(),
								(Selecao) selecao.toBusiness(),
								"O(A) usuario(a) " + usuario.getNome() + " removeu a etapa " + etapa.getTitulo()
										+ " na seleção " + selecao.getTitulo() + " em " + LocalDate.now() + "."));
				session.setAttribute("selecao", selecao);
				session.setAttribute("mensagem", "Etapa removida com sucesso!");
				session.setAttribute("status", "success");
				return "redirect:/selecao/" + selecao.getCodSelecao();
			} else {
				return "error/404";
			}
		} catch (IllegalAccessException e) {
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "danger");
			return "redirect:/selecao/" + codSelecao;
		} catch (IllegalCodeException e) {
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "danger");
			return "redirect:/selecao/" + codSelecao;
		} catch (Exception e) {
			session.setAttribute("mensagem", e.getMessage());
			session.setAttribute("status", "danger");
			return "redirect:/selecao/" + codSelecao;
		}
	}

}
