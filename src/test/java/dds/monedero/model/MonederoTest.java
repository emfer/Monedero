package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  void Poner() {
    cuenta.poner(1500);
    assertEquals(cuenta.getSaldo(),1500);
    
  }
  @Test
  void Sacar() {
    cuenta.poner(1500);
    cuenta.sacar(500);
    assertEquals(cuenta.getSaldo(),1000);
    
  }

  @Test
  void PonerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.poner(-1500));
  }

  @Test
  void TresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    assertEquals(cuenta.getMovimientos().size(), 3);
  }
  
  @Test
  void FallaEnDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    assertFalse(cuenta.getMovimientos().size()==2);
  }

  @Test
  void MasDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.poner(1500);
          cuenta.poner(456);
          cuenta.poner(1900);
          cuenta.poner(245);
    });
  }

  @Test
  void ExtraerMasQueElSaldo() {
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.setSaldo(90);
          cuenta.sacar(1001);
    });
  }
  
  @Test
  void ComparoConCuentaDistinta() {
   Cuenta cuentaSecundaria = new Cuenta();
   cuentaSecundaria.setSaldo(4000);
   cuentaSecundaria.sacar(300);
   cuentaSecundaria.sacar(200);
   cuentaSecundaria.poner(2000);
   double importe = cuentaSecundaria.getSaldo();
   assertTrue(importe>cuenta.getSaldo());
   
  }

  @Test
  public void ExtraerMasDe1000() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.sacar(1001);
    });
  }
  
  @Test
  public void ExtraerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> {
      cuenta.setSaldo(200);
      cuenta.sacar(-1);
    });
  }

 
}