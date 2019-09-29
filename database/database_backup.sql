--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5 (Ubuntu 11.5-1.pgdg18.04+1)
-- Dumped by pg_dump version 11.5 (Ubuntu 11.5-1.pgdg18.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: courses; Type: TABLE; Schema: public; Owner: docker
--

CREATE TABLE public.courses (
    course_id character varying(7) NOT NULL,
    department_id text NOT NULL,
    course_num integer NOT NULL,
    course_name text NOT NULL,
    prerequisites text,
    antirequisites text,
    description text
);


ALTER TABLE public.courses OWNER TO docker;

--
-- Name: departments; Type: TABLE; Schema: public; Owner: docker
--

CREATE TABLE public.departments (
    name text NOT NULL
);


ALTER TABLE public.departments OWNER TO docker;

--
-- Name: programs; Type: TABLE; Schema: public; Owner: docker
--

CREATE TABLE public.programs (
    program_id integer NOT NULL,
    department_id text NOT NULL,
    program_name text NOT NULL,
    courses text
);


ALTER TABLE public.programs OWNER TO docker;

--
-- Name: programs_program_id_seq; Type: SEQUENCE; Schema: public; Owner: docker
--

CREATE SEQUENCE public.programs_program_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.programs_program_id_seq OWNER TO docker;

--
-- Name: programs_program_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: docker
--

ALTER SEQUENCE public.programs_program_id_seq OWNED BY public.programs.program_id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: docker
--

CREATE TABLE public.users (
    id text NOT NULL,
    isadmin boolean NOT NULL,
    salt character varying(32) NOT NULL,
    passwordhash character varying(64) NOT NULL
);


ALTER TABLE public.users OWNER TO docker;

--
-- Name: programs program_id; Type: DEFAULT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.programs ALTER COLUMN program_id SET DEFAULT nextval('public.programs_program_id_seq'::regclass);


--
-- Data for Name: courses; Type: TABLE DATA; Schema: public; Owner: docker
--

COPY public.courses (course_id, department_id, course_num, course_name, prerequisites, antirequisites, description) FROM stdin;
CPSC231	Computing	231	Intro to Computing I			
CPSC233	Computing	233	Intro to Computing II			
CPSC235	Computing	235	Advanced Intro to Computing			
HTST200	Arts	200	Events and Ideas that Shook the World			
HTST201	Arts	201	The History of Europe			
HTST202	Arts	202	An Introduction to Military History			
HTST300	Arts	300	The Practice of History			Provides a grounding in the methods and practice of history.
\.


--
-- Data for Name: departments; Type: TABLE DATA; Schema: public; Owner: docker
--

COPY public.departments (name) FROM stdin;
Arts
Business
Computing
Engineering
\.


--
-- Data for Name: programs; Type: TABLE DATA; Schema: public; Owner: docker
--

COPY public.programs (program_id, department_id, program_name, courses) FROM stdin;
5	Arts	History	\N
6	Arts	Economics	\N
7	Arts	Geography	\N
8	Arts	Psychology	\N
9	Arts	Sociology	\N
10	Business	Finance	\N
11	Business	Marketing	\N
12	Business	Accounting	\N
13	Business	Operations Management	\N
14	Business	Supply Chain Management	\N
15	Business	Human Resources	\N
16	Computing	Computer Science	\N
17	Computing	Software Engineering	\N
18	Computing	Artificial Intelligence	\N
19	Computing	Web Development Certification	\N
20	Computing	Data Analytics Diploma	\N
21	Engineering	Electrical Engineering	\N
22	Engineering	Civil Engineering	\N
23	Engineering	Mechanical Engineering	\N
24	Engineering	Geomatics Engineering	\N
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: docker
--

COPY public.users (id, isadmin, salt, passwordhash) FROM stdin;
admin	t	fc19d224bc86968813ea62be7a4b7be8	0b1cf29cfbafd31c6228b253b1a2aed3cf35e175348a68e97ee6fa96abf56559
student	f	56ce6d14f1e64365ec366ec0187e1e82	a7c2bfd0b018bd063e8859b03d3ae3eabe12f9956db6d058516b977d67d197ee
\.


--
-- Name: programs_program_id_seq; Type: SEQUENCE SET; Schema: public; Owner: docker
--

SELECT pg_catalog.setval('public.programs_program_id_seq', 24, true);


--
-- Name: courses courses_pkey; Type: CONSTRAINT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT courses_pkey PRIMARY KEY (course_id);


--
-- Name: departments departments_pkey; Type: CONSTRAINT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT departments_pkey PRIMARY KEY (name);


--
-- Name: programs programs_pkey; Type: CONSTRAINT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.programs
    ADD CONSTRAINT programs_pkey PRIMARY KEY (program_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: courses courses_department_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT courses_department_id_fkey FOREIGN KEY (department_id) REFERENCES public.departments(name) ON DELETE CASCADE;


--
-- Name: programs programs_department_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.programs
    ADD CONSTRAINT programs_department_id_fkey FOREIGN KEY (department_id) REFERENCES public.departments(name) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

