package br.ufg.inf.fs.ctrl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufg.inf.fs.Messages;
import br.ufg.inf.fs.business.HospedeBusiness;
import br.ufg.inf.fs.entities.Hospede;
import br.ufg.inf.fs.exceptions.HospedeException;

@RestController
@RequestMapping(value = "hospedes")
public class HospedeCtrl {

  @Autowired
  private HospedeBusiness business;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<List<Hospede>> findAll() {
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.OK;
    List<Hospede> list = new ArrayList<Hospede>();
    try {
      list = business.findAll();
      if (list.size() == 0) {
        headers.add("message", Messages.get("0407"));
      }
    } catch (Exception e) {
      status = HttpStatus.BAD_REQUEST;
      headers.add("message", Messages.get("0002"));
    }
    return new ResponseEntity<List<Hospede>>(list, headers, status);
  }

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<Hospede> findById(@PathVariable Integer id) {
    Hospede retorno = new Hospede();

    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.OK;
    try {
      retorno = business.findById(id);
      if (retorno == null) {
        headers.add("message", Messages.get("0407"));
      }
    } catch (Exception e) {
      status = HttpStatus.BAD_REQUEST;
      headers.add("message", Messages.get("0002"));
    }
    return new ResponseEntity<Hospede>(retorno, headers, status);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<Hospede> insert(@RequestBody Hospede hospede) {
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.CREATED;

    try {
      hospede = business.insert(hospede);
      headers.add("message", Messages.get("0401"));
    } catch (HospedeException e) {
      headers.add("message", Messages.get(e.getMessage()));
      status = HttpStatus.BAD_REQUEST;
    } catch (Exception e) {
      headers.add("message", Messages.get("0102"));
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<Hospede>(hospede, headers, status);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping
  public ResponseEntity<Hospede> update(@RequestBody Hospede hospede) {

    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.OK;

    try {
      hospede = business.update(hospede);
      headers.add("message", Messages.get("0403"));
    } catch (HospedeException e) {
      headers.add("message", Messages.get(e.getMessage()));
      status = HttpStatus.BAD_REQUEST;
    } catch (Exception e) {
      headers.add("message", Messages.get("0404"));
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<Hospede>(hospede, headers, status);

  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.NO_CONTENT;

    try {
      business.delete(id);
      headers.add("message", Messages.get("0405"));
    } catch (Exception e) {
      headers.add("message", Messages.get("0406"));
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<Void>(headers, status);
  }
}
