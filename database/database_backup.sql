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
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: docker
--

COPY public.users (id, isadmin, salt, passwordhash) FROM stdin;
admin	t	fc19d224bc86968813ea62be7a4b7be8	0b1cf29cfbafd31c6228b253b1a2aed3cf35e175348a68e97ee6fa96abf56559
student	f	56ce6d14f1e64365ec366ec0187e1e82	a7c2bfd0b018bd063e8859b03d3ae3eabe12f9956db6d058516b977d67d197ee
\.


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

