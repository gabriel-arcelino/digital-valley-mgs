package br.ufc.russas.n2s.darwin.controller;

import br.ufc.russas.n2s.darwin.beans.AvaliacaoBeans;
import br.ufc.russas.n2s.darwin.beans.EtapaBeans;
import br.ufc.russas.n2s.darwin.beans.ParticipanteBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.model.EnumCriterioDeAvaliacao;
import br.ufc.russas.n2s.darwin.model.EnumEstadoAvaliacao;
import br.ufc.russas.n2s.darwin.model.EnumPermissao;
import br.ufc.russas.n2s.darwin.model.Log;
import br.ufc.russas.n2s.darwin.model.Selecao;
import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;
import br.ufc.russas.n2s.darwin.service.AvaliacaoServiceIfc;
import br.ufc.russas.n2s.darwin.service.EtapaServiceIfc;
import br.ufc.russas.n2s.darwin.service.LogServiceIfc;
import br.ufc.russas.n2s.darwin.service.ParticipanteServiceIfc;
import br.ufc.russas.n2s.darwin.service.SelecaoServiceIfc;
import br.ufc.russas.n2s.darwin.util.Facade;
import util.Constantes;

import java.time.LocalDate;
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

/**
 *
 * @author Wallison Carlos
 */
@Controller("avaliarController")
@RequestMapping("/avaliar")
public class AvaliarController {

	private EtapaServiceIfc etapaServiceIfc;
	private AvaliacaoServiceIfc avaliacaoServiceIfc;
	private ParticipanteServiceIfc participanteServiceIfc;
	private LogServiceIfc logServiceIfc;
	private SelecaoServiceIfc selecaoServiceIfc;

	@Autowired(required = true)
	public void setEtapaServiceIfc(@Qualifier("etapaServiceIfc") EtapaServiceIfc etapaServiceIfc) {
		this.etapaServiceIfc = etapaServiceIfc;
	}

	@Autowired(required = true)
	public void setSelecaoServiceIfc(@Qualifier("selecaoServiceIfc") SelecaoServiceIfc selecaoServiceIfc) {
		this.selecaoServiceIfc = selecaoServiceIfc;
	}

	@Autowired(required = true)
	public void setAvaliacaoServiceIfc(@Qualifier("avaliacaoServiceIfc") AvaliacaoServiceIfc avaliacaoServiceIfc) {
		this.avaliacaoServiceIfc = avaliacaoServiceIfc;
	}

	@Autowired(required = true)
	public void setParticipanteServiceIfc(
			@Qualifier("participanteServiceIfc") ParticipanteServiceIfc participanteServiceIfc) {
		this.participanteServiceIfc = participanteServiceIfc;
	}

	public LogServiceIfc getLogServiceIfc() {
		return logServiceIfc;
	}

	@Autowired(required = true)
	public void setLogServiceIfc(@Qualifier("logServiceIfc") LogServiceIfc logServiceIfc) {
		this.logServiceIfc = logServiceIfc;
	}

	@RequestMapping(value = "/{codEtapa}", method = RequestMethod.GET)
	public String getIndex(@PathVariable long codEtapa, Model model, HttpServletRequest request) {
		EtapaBeans etapa = etapaServiceIfc.getEtapa(codEtapa);
		HttpSession session = request.getSession();
		SelecaoBeans selecao = (SelecaoBeans) session.getAttribute("selecao");
		UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
		if (etapa.getAvaliadores().contains(usuario) || (selecao.getResponsaveis().contains(usuario))
				|| (usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR))) {
			model.addAttribute("etapa", etapa);
			model.addAttribute("participantesEtapa", etapa.getParticipantes());
			model.addAttribute("avaliador", usuario);
			return "avaliar";
		} else {
			return "error/404";
		}
	}

	@RequestMapping(value = "/inscricao/{codEtapa}", method = RequestMethod.GET)
	public String getIndexInscricao(@PathVariable long codEtapa, Model model, HttpServletRequest request) {
		EtapaBeans etapa = etapaServiceIfc.getEtapa(codEtapa);
		HttpSession session = request.getSession();
		SelecaoBeans selecao = (SelecaoBeans) session.getAttribute("selecao");
		UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
		if (etapa.getAvaliadores().contains(usuario) || (selecao.getResponsaveis().contains(usuario))
				|| (usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR))) {
			model.addAttribute("avaliador", usuario);
			model.addAttribute("etapa", etapa);
			model.addAttribute("participantesEtapa", etapa.getParticipantes());
			return "avaliar";
		} else {
			return "error/404";
		}
	}

	@RequestMapping(value = "/inscricao/{codEtapa}", method = RequestMethod.POST)
	public String avaliarInscricao(@PathVariable long codEtapa, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		UsuarioBeans avaliador = (UsuarioBeans) session.getAttribute("usuarioDarwin");
		EtapaBeans etapa = null;
		try {
			etapa = etapaServiceIfc.getEtapa(codEtapa);
			AvaliacaoBeans avaliacao = new AvaliacaoBeans();
			if (etapa.getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.APROVACAO) {
				avaliacao.setAprovado((Integer.parseInt(request.getParameter("aprovacao")) == 1));
			} else if (etapa.getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.DEFERIMENTO) {
				if (request.getParameter("deferimento") != null) {
					avaliacao.setAprovado((Integer.parseInt(request.getParameter("deferimento")) == 1));
				} else {
					throw new IllegalArgumentException("Não foi selecionado um resultado para o participante!");
				}
			} else if (etapa.getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.NOTA) {
				float nota = Float.parseFloat(request.getParameter("nota"));
				avaliacao.setNota(nota);
				if (nota >= etapa.getNotaMinima()) {
					avaliacao.setAprovado(true);
				} else {
					avaliacao.setAprovado(false);
				}
			}

			avaliacao.setObservacao(request.getParameter("observacoes"));
			ParticipanteBeans participante = participanteServiceIfc
					.getParticipante(Long.parseLong(request.getParameter("participante")));
			avaliacao.setParticipante(participante);
			avaliacao.setAvaliador(avaliador);
			avaliacao.setEstado(EnumEstadoAvaliacao.AVALIADO);
			etapaServiceIfc.setUsuario(avaliador);
			etapaServiceIfc.avalia(etapa, avaliacao);
			etapa = this.etapaServiceIfc.getEtapa(etapa.getCodEtapa());
			this.getLogServiceIfc().adicionaLog(new Log(LocalDate.now(), (UsuarioDarwin) avaliador.toBusiness(),
					(Selecao) ((SelecaoBeans) session.getAttribute("selecao")).toBusiness(),
					"O(A) usuario(a) " + avaliador.getNome() + " realizou a avaliação do candidato detentor do CPF: "
							+ participante.getCandidato().getCPF() + " na etapa " + etapa.getTitulo() + " da seleção "
							+ ((SelecaoBeans) session.getAttribute("selecao")).getTitulo() + " em " + LocalDate.now()
							+ "."));
			model.addAttribute("etapa", etapa);
			model.addAttribute("avaliador", avaliador);
			model.addAttribute("participantesEtapa", etapa.getParticipantes());
			model.addAttribute("mensagem", "Participante avaliado com sucesso!");
			model.addAttribute("status", "success");
			return "avaliar";
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			model.addAttribute("mensagem", e.getMessage());
			model.addAttribute("avaliador", avaliador);
			model.addAttribute("participantesEtapa", etapa.getParticipantes());
			model.addAttribute("status", "danger");
			return "avaliar";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			model.addAttribute("mensagem", "Isso não é um número!");
			model.addAttribute("avaliador", avaliador);
			model.addAttribute("participantesEtapa", etapa.getParticipantes());
			model.addAttribute("status", "danger");
			return "avaliar";
		} catch (NullPointerException | IllegalArgumentException e) {
			e.printStackTrace();
			model.addAttribute("mensagem", e.getMessage());
			model.addAttribute("avaliador", avaliador);
			model.addAttribute("participantesEtapa", etapa.getParticipantes());
			model.addAttribute("status", "danger");
			return "avaliar";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("mensagem", e.getMessage());
			model.addAttribute("avaliador", avaliador);
			model.addAttribute("participantesEtapa", etapa.getParticipantes());
			model.addAttribute("status", "danger");
			return "avaliar";
		}
	}

	@RequestMapping(value = "/{codEtapa}", method = RequestMethod.POST)
	public String avaliarEtapa(@PathVariable long codEtapa, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		try {
			EtapaBeans etapa = etapaServiceIfc.getEtapa(codEtapa);
			AvaliacaoBeans avaliacao = new AvaliacaoBeans();
			if (etapa.getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.APROVACAO) {
				if (request.getParameter("aprovacao") != null) {
					avaliacao.setAprovado((Integer.parseInt(request.getParameter("aprovacao")) == 1));
				} else {
					throw new IllegalArgumentException("Não foi selecionado um resultado para o participante!");
				}
			} else if (etapa.getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.DEFERIMENTO) {
				if (request.getParameter("deferimento") != null) {
					avaliacao.setAprovado((Integer.parseInt(request.getParameter("deferimento")) == 1));
				} else {
					throw new IllegalArgumentException("Não foi selecionado um resultado para o participante!");
				}
			} else if (etapa.getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.NOTA) {
				if (request.getParameter("nota") != null) {
					float nota = Float.parseFloat(request.getParameter("nota"));
					avaliacao.setNota(nota);
					if (nota >= etapa.getNotaMinima()) {
						avaliacao.setAprovado(true);
					} else {
						avaliacao.setAprovado(false);
					}
				} else {
					throw new IllegalArgumentException("Não foi selecionado uma nota para o participante!");
				}
			}

			UsuarioBeans avaliador = (UsuarioBeans) session.getAttribute("usuarioDarwin");
			avaliacao.setObservacao(request.getParameter("observacoes"));
			ParticipanteBeans participante = participanteServiceIfc
					.getParticipante(Long.parseLong(request.getParameter("participante")));
			avaliacao.setParticipante(participante);
			avaliacao.setAvaliador(avaliador);
			avaliacao.setEstado(EnumEstadoAvaliacao.AVALIADO);
			// avaliacaoServiceIfc.setUsuario(avaliador);
			avaliacao = avaliacaoServiceIfc.adicionaAvaliacao(avaliacao);
			etapaServiceIfc.setUsuario(avaliador);
			etapaServiceIfc.avalia(etapa, avaliacao);
			session.setAttribute("mensagem", "Participante avaliado com sucesso!");
			session.setAttribute("status", "success");
			return "redirect: " + Constantes.getAppUrl() + "/avaliar/" + etapa.getCodEtapa();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			model.addAttribute("mensagem", e.getMessage());
			model.addAttribute("status", "danger");
			return "avaliar";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			model.addAttribute("mensagem", "Isso não é um número!");
			model.addAttribute("status", "danger");
			return "avaliar";
		} catch (NullPointerException | IllegalArgumentException e) {
			e.printStackTrace();
			model.addAttribute("mensagem", e.getMessage());
			model.addAttribute("status", "danger");
			return "avaliar";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("mensagem", e.getMessage());
			model.addAttribute("status", "danger");
			return "avaliar";
		}
	}

	@RequestMapping(value = "/recurso/etapa/{codEtapa}/avaliacao/{codAvaliacao}", method = RequestMethod.POST)
	public String avaliarRecurso(@PathVariable long codAvaliacao, @PathVariable long codEtapa,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		EtapaBeans etapa = etapaServiceIfc.getEtapa(codEtapa);
		AvaliacaoBeans avaliacao = avaliacaoServiceIfc.getAvaliacao(codAvaliacao);
		try {
			if (etapa.getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.NOTA) {
				float novaNota = Float.parseFloat(request.getParameter("nota"));
				avaliacao.setNota(novaNota);
				avaliacao.setAprovado(novaNota >= etapa.getNotaMinima());
			} else {
				boolean novoEstado = (request.getParameter("estado") != null
						&& request.getParameter("estado").equals("1"));
				avaliacao.setAprovado(novoEstado);
			}

			avaliacaoServiceIfc.atualizarAvaliacao(avaliacao);
			session.setAttribute("mensagem", "Avaliação atualizada com sucesso!");
			session.setAttribute("status", "success");

		} catch (Exception e) {
			session.setAttribute("mensagem", "Erro ao atualizar avaliação!");
			session.setAttribute("status", "danger");
		}
		return "redirect: " + Constantes.getAppUrl() + "/recursoEtapa/" + etapa.getCodEtapa() + "/"
				+ avaliacao.getParticipante().getCodParticipante();
	}

	@RequestMapping(value = "/download/{codSelecao}/{codEtapa}/{codParticipante}", method = RequestMethod.GET)
	public String getParticipantesInscricao(@PathVariable long codSelecao, @PathVariable long codEtapa,
			@PathVariable long codParticipante, Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		EtapaBeans etapa = etapaServiceIfc.getEtapa(codEtapa);
		SelecaoBeans selecao = selecaoServiceIfc.getSelecao(codSelecao);
		try {
			UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
			ParticipanteBeans p = this.participanteServiceIfc.getParticipante(codParticipante);
			if ((etapa.getAvaliadores().contains(usuario))
					|| (usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)
							|| (usuario.getCodUsuario() == p.getCandidato().getCodUsuario()))) {
				Facade.compactarParaZip(selecao, etapa, p, response);
				return (String) request.getAttribute("javax.servlet.forward.request_uri");
			} else {
				return "error/404";
			}
		} catch (Exception e) {
			session.setAttribute("mensagem", "Erro ao buscar documentação!");
			session.setAttribute("status", "danger");
			return "redirect: " + Constantes.getAppUrl() + "/avaliar/" + etapa.getCodEtapa();
		}
	}

	@RequestMapping(value = "atualizar/{codEtapa}/{codAvaliacao}", method = RequestMethod.POST)
	public String atualizarEtapa(@PathVariable long codEtapa, @PathVariable long codAvaliacao,
			HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		try {
			EtapaBeans etapa = etapaServiceIfc.getEtapa(codEtapa);
			AvaliacaoBeans avaliacao = new AvaliacaoBeans();
			avaliacao.setCodAvaliacao(codAvaliacao);
			if (etapa.getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.APROVACAO) {
				if (request.getParameter("aprovacao") != null) {
					avaliacao.setAprovado((Integer.parseInt(request.getParameter("aprovacao")) == 1));
				} else {
					throw new IllegalArgumentException("Não foi selecionado um resultado para o participante!");
				}
			} else if (etapa.getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.DEFERIMENTO) {
				if (request.getParameter("deferimento") != null) {
					avaliacao.setAprovado((Integer.parseInt(request.getParameter("deferimento")) == 1));
				} else {
					throw new IllegalArgumentException("Não foi selecionado um resultado para o participante!");
				}
			} else if (etapa.getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.NOTA) {
				if (request.getParameter("nota") != null) {
					float nota = Float.parseFloat(request.getParameter("nota"));
					avaliacao.setNota(nota);
					if (nota >= etapa.getNotaMinima()) {
						avaliacao.setAprovado(true);
					} else {
						avaliacao.setAprovado(false);
					}
				} else {
					throw new IllegalArgumentException("Não foi selecionado uma nota para o participante!");
				}
			}

			UsuarioBeans avaliador = (UsuarioBeans) session.getAttribute("usuarioDarwin");
			avaliacao.setObservacao(request.getParameter("observacoes"));
			ParticipanteBeans participante = participanteServiceIfc
					.getParticipante(Long.parseLong(request.getParameter("participante")));
			avaliacao.setParticipante(participante);
			avaliacao.setAvaliador(avaliador);
			avaliacao.setEstado(EnumEstadoAvaliacao.AVALIADO);
			avaliacaoServiceIfc.setUsuario(avaliador);

			etapa.setAvaliacoes(etapa.getAvaliacoes().stream().filter(av -> av.getCodAvaliacao() != codAvaliacao)
					.collect(Collectors.toList()));

			etapaServiceIfc.setUsuario(avaliador);
			etapaServiceIfc.atualizaEtapa(etapa);

			avaliacaoServiceIfc.removeAvaliacao(avaliacao);
			avaliacao.setCodAvaliacao(0); // Setado como zero para simular a criação de um novo objeto
			avaliacao.setEstado(EnumEstadoAvaliacao.AVALIADO);
			etapaServiceIfc.avalia(etapa, avaliacao);

			// Foi feito assim pq o atualizar do DAO de avaliação não funcionou
			// de jeito algum
			// Primeiro foi removido a atualização antiga
			// E uma nova foi adicionada, simulando uma atualização

			session.setAttribute("mensagem", "Avaliação do participante atualizada com sucesso!");
			session.setAttribute("status", "success");

			if (request.getRequestURI().contains("inscricao")) {
				return "redirect: " + Constantes.getAppUrl() + "/avaliar/inscricao/" + etapa.getCodEtapa();
			}
			return "redirect: " + Constantes.getAppUrl() + "/avaliar/" + etapa.getCodEtapa();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			model.addAttribute("mensagem", "Isso não é um número!");
			model.addAttribute("status", "danger");
			return "avaliar";
		} catch (NullPointerException | IllegalArgumentException e) {
			e.printStackTrace();
			model.addAttribute("mensagem", e.getMessage());
			model.addAttribute("status", "danger");
			return "avaliar";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("mensagem", e.getMessage());
			model.addAttribute("status", "danger");
			return "avaliar";
		}
	}

}
