-- PUBLIC ----

CREATE SCHEMA IF NOT EXISTS public;

COMMENT ON SCHEMA public IS 'standard public schema';

CREATE TYPE public.tiposolicitacao AS ENUM (
    'RECORRECAO',
    'SEGUNDA_CHAMADA'
);

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE public.aluno (
    matricula character varying(6) NOT NULL,
    semestre_ingresso character varying(6),
    id_pessoa_usuario integer NOT NULL,
    id_curso integer
);

CREATE TABLE public.arquivo (
    codarquivo bigint NOT NULL,
    arquivo bytea,
    data bytea,
    titulo character varying(255)
);

CREATE SEQUENCE public.arquivo_codarquivo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.arquivo_codarquivo_seq OWNED BY public.arquivo.codarquivo;

CREATE TABLE public.curso (
    nome_curso character varying(30) NOT NULL,
    id_curso integer NOT NULL,
    professor_coordenador_id integer
);

CREATE TABLE public.disciplina (
    nome character varying NOT NULL,
    id_disciplina integer NOT NULL,
    id_curso integer NOT NULL,
    id_professor integer
);

CREATE SEQUENCE public.id_perfil
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.modulo (
    id_modulo integer NOT NULL,
    titulo character varying(60) NOT NULL,
    url character varying(200) NOT NULL,
    imagem character varying(60) NOT NULL,
    descricao character varying
);

CREATE SEQUENCE public.modulo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.modulo_id_seq OWNED BY public.modulo.id_modulo;

CREATE TABLE public.perfil (
    id integer DEFAULT nextval('public.id_perfil'::regclass) NOT NULL,
    nome character varying(30) NOT NULL
);

CREATE TABLE public.perfil_modulo (
    id_perfil integer NOT NULL,
    id_modulo integer NOT NULL
);

CREATE TABLE public.periodo (
    codperiodo bigint NOT NULL,
    inicio bytea,
    termino bytea
);

CREATE SEQUENCE public.periodo_codperiodo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.periodo_codperiodo_seq OWNED BY public.periodo.codperiodo;

CREATE TABLE public.pessoa_usuario (
    data_nascimento date,
    nome character varying(60) NOT NULL,
    cpf character varying(11),
    email character varying(60),
    login character varying(30),
    senha character varying(90),
    id_pessoa_usuario integer NOT NULL,
    nivel integer DEFAULT 2 NOT NULL,
    token_sessao character varying(60),
    data_ultima_sessao date,
    token_recuperacao character varying(60),
    token_usuario character varying(60),
    imagem character varying,
    data_ultima_recuperacao date,
    perfil integer NOT NULL
);

CREATE SEQUENCE public.pessoa_usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.pessoa_usuario_id_seq OWNED BY public.pessoa_usuario.id_pessoa_usuario;

CREATE TABLE public.pre_cadastro_aluno (
    matricula character varying(6) NOT NULL,
    nome character varying(50) NOT NULL,
    id_curso integer NOT NULL
);

CREATE TABLE public.pre_cadastro_servidor (
    siape character varying(7) NOT NULL,
    nome character varying(50) NOT NULL
);

CREATE TABLE public.professor (
    id_pessoa_prof integer NOT NULL
);

CREATE TABLE public.professor_disciplina (
    id_professor integer NOT NULL,
    id_disciplina integer NOT NULL
);

CREATE TABLE public.servidor (
    siape character varying(7) NOT NULL,
    id_pessoa_usuario integer NOT NULL,
    cargo character varying(20) NOT NULL
);

CREATE SEQUENCE public.todos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.usuario_modulo (
    id_usuario integer NOT NULL,
    id_modulo integer NOT NULL,
    permissao boolean NOT NULL
);

ALTER TABLE ONLY public.arquivo ALTER COLUMN codarquivo SET DEFAULT nextval('public.arquivo_codarquivo_seq'::regclass);

ALTER TABLE ONLY public.modulo ALTER COLUMN id_modulo SET DEFAULT nextval('public.modulo_id_seq'::regclass);

ALTER TABLE ONLY public.periodo ALTER COLUMN codperiodo SET DEFAULT nextval('public.periodo_codperiodo_seq'::regclass);

ALTER TABLE ONLY public.pessoa_usuario ALTER COLUMN id_pessoa_usuario SET DEFAULT nextval('public.pessoa_usuario_id_seq'::regclass);

-- DARWIN ----

CREATE SCHEMA IF NOT EXISTS darwin;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE darwin.aditivos_selecao (
    selecao bigint NOT NULL,
    arquivo bigint NOT NULL
);

CREATE TABLE darwin.anexos_selecao (
    selecao bigint NOT NULL,
    arquivo bigint NOT NULL
);

CREATE TABLE darwin.arquivo (
    codarquivo bigint NOT NULL,
    arquivo bytea,
    data bytea,
    titulo character varying(255)
);

CREATE SEQUENCE darwin.arquivo_codarquivo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE darwin.arquivo_codarquivo_seq OWNED BY darwin.arquivo.codarquivo;

CREATE TABLE darwin.arquivos_documentacao (
    documentacao bigint NOT NULL,
    arquivo bigint NOT NULL
);

CREATE TABLE darwin.avaliacao (
    codavaliacao bigint NOT NULL,
    aprovado boolean NOT NULL,
    estado integer,
    nota real NOT NULL,
    observacao character varying(255),
    avaliador bigint,
    participante bigint,
    avaliado bigint
);

CREATE SEQUENCE darwin.avaliacao_codavaliacao_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE darwin.avaliacao_codavaliacao_seq OWNED BY darwin.avaliacao.codavaliacao;

CREATE TABLE darwin.avaliacoes (
    etapa bigint NOT NULL,
    avaliacao bigint NOT NULL
);

CREATE TABLE darwin.avaliadores (
    etapa bigint NOT NULL,
    avaliador bigint NOT NULL
);

CREATE TABLE darwin.documentacao (
    coddocumentacao bigint NOT NULL,
    candidato bigint
);

CREATE SEQUENCE darwin.documentacao_coddocumentacao_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE darwin.documentacao_coddocumentacao_seq OWNED BY darwin.documentacao.coddocumentacao;

CREATE TABLE darwin.documentacoes (
    etapa bigint NOT NULL,
    documentacao bigint NOT NULL
);

CREATE TABLE darwin.documentacoes_exigidas (
    codetapa bigint NOT NULL,
    documentacao_exigida character varying(255)
);

CREATE TABLE darwin.documentacoes_opcionais (
    codetapa bigint NOT NULL,
    documentacao_opcional character varying(255)
);

CREATE TABLE darwin.etapa (
    codetapa bigint NOT NULL,
    criterio_de_avaliacao integer,
    criteriodesempate boolean NOT NULL,
    descricao text,
    divulgadoresultado boolean NOT NULL,
    estado integer,
    limiteclassificados integer NOT NULL,
    notaminima real NOT NULL,
    pesonota real NOT NULL,
    posiscaocriteriodesempate integer NOT NULL,
    titulo character varying(255),
    periodo bigint,
    prerequisito bigint,
    recurso bigint
);

CREATE SEQUENCE darwin.etapa_codetapa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE darwin.etapa_codetapa_seq OWNED BY darwin.etapa.codetapa;

CREATE TABLE darwin.etapas_selecao (
    selecao bigint NOT NULL,
    etapa bigint NOT NULL
);

CREATE TABLE darwin.log (
    codlog bigint NOT NULL,
    data bytea,
    modificacao text,
    selecao bigint,
    usuario bigint
);

CREATE SEQUENCE darwin.log_codlog_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE darwin.log_codlog_seq OWNED BY darwin.log.codlog;

CREATE TABLE darwin.participante (
    codparticipante bigint NOT NULL,
    datainscricao bytea,
    deferido boolean NOT NULL,
    notificado boolean NOT NULL,
    candidato bigint
);

CREATE SEQUENCE darwin.participante_codparticipante_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE darwin.participante_codparticipante_seq OWNED BY darwin.participante.codparticipante;

CREATE TABLE darwin.participantes_etapa (
    etapa bigint NOT NULL,
    participante bigint NOT NULL
);

CREATE TABLE darwin.periodo (
    codperiodo bigint NOT NULL,
    inicio bytea,
    termino bytea
);

CREATE SEQUENCE darwin.periodo_codperiodo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE darwin.periodo_codperiodo_seq OWNED BY darwin.periodo.codperiodo;

CREATE TABLE darwin.recurso (
    codrecurso bigint NOT NULL,
    descricao text,
    periodo bigint
);

CREATE SEQUENCE darwin.recurso_codrecurso_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE darwin.recurso_codrecurso_seq OWNED BY darwin.recurso.codrecurso;

CREATE TABLE darwin.responsaveis_selecao (
    selecao bigint NOT NULL,
    usuario bigint NOT NULL
);

CREATE TABLE darwin.selecao (
    codselecao bigint NOT NULL,
    areadeconcentracao character varying(255),
    categoria character varying(255),
    descricao text,
    descricaoprerequisitos text,
    divulgada boolean NOT NULL,
    divulgadoresultado boolean NOT NULL,
    estado integer,
    titulo character varying(255),
    vagasremuneradas integer NOT NULL,
    vagasvoluntarias integer NOT NULL,
    edital bigint,
    etapa_inscricao bigint,
    periodo bigint,
    deletada boolean DEFAULT false NOT NULL,
    exibirnotas boolean DEFAULT true NOT NULL
);

COMMENT ON COLUMN darwin.selecao.deletada IS 'indica que a seleção foi apagada virtualmente';

CREATE SEQUENCE darwin.selecao_codselecao_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE darwin.selecao_codselecao_seq OWNED BY darwin.selecao.codselecao;

CREATE TABLE darwin.usuario (
    codusuario bigint NOT NULL,
    cpf character varying(255),
    codusuariocontroledeacesso bigint,
    email character varying(255),
    nome character varying(255),
    recebeemail boolean NOT NULL
);

CREATE SEQUENCE darwin.usuario_codusuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE darwin.usuario_codusuario_seq OWNED BY darwin.usuario.codusuario;

CREATE TABLE darwin.usuariodarwin_permissoes (
    usuariodarwin_codusuario bigint NOT NULL,
    permissoes integer
);

ALTER TABLE ONLY darwin.arquivo ALTER COLUMN codarquivo SET DEFAULT nextval('darwin.arquivo_codarquivo_seq'::regclass);

ALTER TABLE ONLY darwin.avaliacao ALTER COLUMN codavaliacao SET DEFAULT nextval('darwin.avaliacao_codavaliacao_seq'::regclass);

ALTER TABLE ONLY darwin.documentacao ALTER COLUMN coddocumentacao SET DEFAULT nextval('darwin.documentacao_coddocumentacao_seq'::regclass);

ALTER TABLE ONLY darwin.etapa ALTER COLUMN codetapa SET DEFAULT nextval('darwin.etapa_codetapa_seq'::regclass);

ALTER TABLE ONLY darwin.log ALTER COLUMN codlog SET DEFAULT nextval('darwin.log_codlog_seq'::regclass);

ALTER TABLE ONLY darwin.participante ALTER COLUMN codparticipante SET DEFAULT nextval('darwin.participante_codparticipante_seq'::regclass);

ALTER TABLE ONLY darwin.periodo ALTER COLUMN codperiodo SET DEFAULT nextval('darwin.periodo_codperiodo_seq'::regclass);

ALTER TABLE ONLY darwin.recurso ALTER COLUMN codrecurso SET DEFAULT nextval('darwin.recurso_codrecurso_seq'::regclass);

ALTER TABLE ONLY darwin.selecao ALTER COLUMN codselecao SET DEFAULT nextval('darwin.selecao_codselecao_seq'::regclass);

ALTER TABLE ONLY darwin.usuario ALTER COLUMN codusuario SET DEFAULT nextval('darwin.usuario_codusuario_seq'::regclass);
