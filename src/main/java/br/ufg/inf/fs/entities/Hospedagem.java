package br.ufg.inf.fs.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "tb_hospedagem")
public class Hospedagem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_hospedagem")
  private Integer idHospedagem;

  @Column(name = "id_quarto")
  private Integer idQuarto;

  @Column(name = "id_hospede")
  private Integer idHospede;

  @Column(name = "dt_checkin")
  @Temporal(TemporalType.DATE)
  private Date dtCheckin;

  @Column(name = "dt_checkout")
  @Temporal(TemporalType.DATE)
  private Date dtCheckout;

  public Hospedagem() {
    super();
  }

  public Hospedagem(Integer idHospedagem, Integer idQuarto, Integer idHospede, Date dtCheckin, Date dtCheckout) {
    super();
    this.idHospedagem = idHospedagem;
    this.idQuarto = idQuarto;
    this.idHospede = idHospede;
    this.dtCheckin = dtCheckin;
    this.dtCheckout = dtCheckout;
  }

  public Integer getIdHospedagem() {
    return idHospedagem;
  }

  public void setIdHospedagem(Integer idHospedagem) {
    this.idHospedagem = idHospedagem;
  }

  public Integer getIdQuarto() {
    return idQuarto;
  }

  public void setIdQuarto(Integer idQuarto) {
    this.idQuarto = idQuarto;
  }

  public Integer getIdHospede() {
    return idHospede;
  }

  public void setIdHospede(Integer idHospede) {
    this.idHospede = idHospede;
  }

  public Date getDtCheckin() {
    return dtCheckin;
  }

  public void setDtCheckin(Date dtCheckin) {
    this.dtCheckin = dtCheckin;
  }

  public Date getDtCheckout() {
    return dtCheckout;
  }

  public void setDtCheckout(Date dtCheckout) {
    this.dtCheckout = dtCheckout;
  }

  @Override
  public String toString() {
    return "Hospedagem [idHospedagem=" + idHospedagem + ", idQuarto=" + idQuarto + ", idHospede=" + idHospede
        + ", dtCheckin=" + dtCheckin + ", dtCheckout=" + dtCheckout + "]";
  }
}
