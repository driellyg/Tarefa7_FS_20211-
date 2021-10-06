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
import br.ufg.inf.fs.business.HospedagemBusiness;
import br.ufg.inf.fs.entities.Hospedagem;
import br.ufg.inf.fs.exceptions.HospedagemException;

@RestController
@RequestMapping(value = "hospedagens")
public class HospedagemCtrl {

  @Autowired
  private HospedagemBusiness business;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<List<Hospedagem>> findAll() {
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.OK;
    List<Hospedagem> list = new ArrayList<Hospedagem>();
    try {
      list = business.findAll();
      if (list.size() == 0) {
        headers.add("message", Messages.get("0307"));
      }
    } catch (Exception e) {
      status = HttpStatus.BAD_REQUEST;
      headers.add("message", Messages.get("0002"));
    }
    return new ResponseEntity<List<Hospedagem>>(list, headers, status);
  }

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<Hospedagem> findById(@PathVariable Integer id) {
    Hospedagem retorno = new Hospedagem();

    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.OK;
    try {
      retorno = business.findById(id);
      if (retorno == null) {
        headers.add("message", Messages.get("0307"));
      }
    } catch (Exception e) {
      status = HttpStatus.BAD_REQUEST;
      headers.add("message", Messages.get("0002"));
    }
    return new ResponseEntity<Hospedagem>(retorno, headers, status);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<Hospedagem> insert(@RequestBody Hospedagem hospedagem) {
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.CREATED;

    try {
      hospedagem = business.insert(hospedagem);
      headers.add("message", Messages.get("0301"));
    } catch (HospedagemException e) {
      headers.add("message", Messages.get(e.getMessage()));
      status = HttpStatus.BAD_REQUEST;
    } catch (Exception e) {
      headers.add("message", Messages.get("0302"));
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<Hospedagem>(hospedagem, headers, status);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping
  public ResponseEntity<Hospedagem> update(@RequestBody Hospedagem hospedagem) {

    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.OK;

    try {
      hospedagem = business.update(hospedagem);
      headers.add("message", Messages.get("0303"));
    } catch (HospedagemException e) {
      headers.add("message", Messages.get(e.getMessage()));
      status = HttpStatus.BAD_REQUEST;
    } catch (Exception e) {
      headers.add("message", Messages.get("0304"));
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<Hospedagem>(hospedagem, headers, status);

  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.NO_CONTENT;

    try {
      business.delete(id);
    } catch (Exception e) {
      headers.add("message", Messages.get("0306"));
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<Void>(headers, status);
  }

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/checkin/{dt}")
  public ResponseEntity<List<Hospedagem>> findByDtCheckin(@PathVariable Date dt) {
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.OK;
    List<Hospedagem> list = new ArrayList<Hospedagem>();
    try {
      list = business.findByCheckinDate(dt);
      if (list.size() == 0) {
        headers.add("message", Messages.get("0309"));
      }
    } catch (Exception e) {
      status = HttpStatus.BAD_REQUEST;
      headers.add("message", Messages.get("0002"));
    }
    return new ResponseEntity<List<Hospedagem>>(list, headers, status);
  }

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/checkout/{dt}")
  public ResponseEntity<List<Hospedagem>> findByDtCheckout(@PathVariable Date dt) {
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.OK;
    List<Hospedagem> list = new ArrayList<Hospedagem>();
    try {
      list = business.findByCheckoutDate(dt);
      if (list.size() == 0) {
        headers.add("message", Messages.get("0309"));
      }
    } catch (Exception e) {
      status = HttpStatus.BAD_REQUEST;
      headers.add("message", Messages.get("0002"));
    }
    return new ResponseEntity<List<Hospedagem>>(list, headers, status);
  }

}
