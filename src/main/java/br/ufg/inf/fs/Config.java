package br.ufg.inf.fs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufg.inf.fs.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.ufg.inf.fs.enums.CategoriaQuarto;
import br.ufg.inf.fs.repositories.HotelRepository;
import br.ufg.inf.fs.repositories.QuartoRepository;
import br.ufg.inf.fs.repositories.HospedeRepository;
import br.ufg.inf.fs.repositories.HospedagemRepository;
import br.ufg.inf.fs.repositories.RegraRepository;
import br.ufg.inf.fs.repositories.UsuarioRepository;

@Configuration
@Profile("dev")
public class Config implements CommandLineRunner {

	@Autowired
	private HotelRepository hoteRepository;

	@Autowired
	private QuartoRepository quartoRepository;

	@Autowired
	private HospedeRepository hospedeRepository;

	@Autowired
	private HospedagemRepository hospedagemRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RegraRepository regraRepository;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		/*
		 * INSERIR NO MEU BANCO DE DADOS INFORMAÇÕES INICIAIS...
		 */

		Hotel h1 = new Hotel(null, "Copacabana Palace", "Rio de Janeiro", 5);
		Hotel h2 = new Hotel(null, "Oitis Hotel", "Goiania", 3);
		Hotel h3 = new Hotel(null, "Beiramar Hotel", "Rio de Janeiro", 4);
		Hotel h4 = new Hotel(null, "Bourbon Hotel", "Sao Paulo", 5);

		h1 = hoteRepository.save(h1);
		h2 = hoteRepository.save(h2);
		h3 = hoteRepository.save(h3);
		h4 = hoteRepository.save(h4);

		Quarto q1 = quartoRepository.save(new Quarto(null, h1, CategoriaQuarto.APARTAMENTO, 3, 1010, 350.0));
		Quarto q2 = quartoRepository.save(new Quarto(null, h2, CategoriaQuarto.SIMPLES, 1, 7, 150.0));
		Quarto q3 = quartoRepository.save(new Quarto(null, h3, CategoriaQuarto.PADRAO, 2, 306, 200.0));
		Quarto q4 = quartoRepository.save(new Quarto(null, h4, CategoriaQuarto.LUXO, 4, 1313, 650.0));

		Hospede hospede1 = hospedeRepository.save(new Hospede(null, "Claudia", new Date(), 701765432));
		Hospede hospede2 = hospedeRepository.save(new Hospede(null, "Isabel", new Date(), 702786743));
		Hospede hospede3 = hospedeRepository.save(new Hospede(null, "Thomas", new Date(), 703214321));
		Hospede hospede4 = hospedeRepository.save(new Hospede(null, "Marcelo", new Date(), 704561032));

		Hospedagem hospedagem1 = hospedagemRepository
				.save(new Hospedagem(null, q1.getIdQuarto(), hospede1.getIdHospede(), new Date(), new Date()));
		Hospedagem hospedagem2 = hospedagemRepository
				.save(new Hospedagem(null, q2.getIdQuarto(), hospede2.getIdHospede(), new Date(), new Date()));
		Hospedagem hospedagem3 = hospedagemRepository
				.save(new Hospedagem(null, q3.getIdQuarto(), hospede3.getIdHospede(), new Date(), new Date()));
		Hospedagem hospedagem4 = hospedagemRepository
				.save(new Hospedagem(null, q4.getIdQuarto(), hospede4.getIdHospede(), new Date(), new Date()));

		Regra r1 = regraRepository.save(new Regra("ADMIN"));
		Regra r2 = regraRepository.save(new Regra("USER"));
		Regra r3 = regraRepository.save(new Regra("GUEST"));

		List<Regra> regras = new ArrayList<Regra>();

		regras.add(r1);
		regras.add(r2);

		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		Usuario usu1 = usuarioRepository.save(new Usuario("luiz", "Luiz Olegario", encoder.encode("1234"), regras));

		regras = new ArrayList<Regra>();

		regras.add(r2);
		regras.add(r3);

		Usuario usu2 = usuarioRepository.save(new Usuario("jose", "Jose Alves", encoder.encode("qwer"), regras));

	}

}
