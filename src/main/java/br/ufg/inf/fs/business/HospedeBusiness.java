package br.ufg.inf.fs.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufg.inf.fs.entities.Hospede;
import br.ufg.inf.fs.exceptions.HospedeException;
import br.ufg.inf.fs.repositories.HospedeRepository;

@Service
public class HospedeBusiness {

  @Autowired
  private HospedeRepository repository;

  public List<Hospede> findAll() {
    return repository.findAll();
  }

  public List<Hospede> findName(String str) {
    return repository.findByNmHospede(str);
  }

  public List<Hospede> findByCpf(Integer cpf) {
    return repository.findByCpf(cpf);
  }

  public Hospede findById(Integer id) {
    Optional<Hospede> retorno = repository.findById(id);
    return retorno.get();
  }

  public Hospede insert(Hospede hospede) throws HospedeException {
    this.validaHospede(hospede);
    return repository.save(hospede);
  }

  public Hospede update(Hospede hospede) throws HospedeException {
    this.validaHospede(hospede);
    return repository.save(hospede);
  }

  public void delete(Integer id) {
    repository.deleteById(id);
  }

  private void validaHospede(Hospede hospede) throws HospedeException {
    if (hospede.getCpf() == null || String.valueOf(hospede.getCpf()).length() != 11) {
      throw new HospedeException("0408");
    }
    if (hospede.getNmHospede() == null) {
      throw new HospedeException("0409");
    }
    if (hospede.getDtNascimento() == null) {
      throw new HospedeException("0410");
    }
  }
}
