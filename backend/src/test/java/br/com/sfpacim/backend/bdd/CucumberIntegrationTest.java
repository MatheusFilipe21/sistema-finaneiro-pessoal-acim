package br.com.sfpacim.backend.bdd;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Classe "Runner" (Executora) que permite ao JUnit 4 (Vintage) descobrir
 * e executar os testes do Cucumber (arquivos .feature).
 *
 * @author Matheus F. N. Pereira
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "br.com.sfpacim.backend.bdd")
public class CucumberIntegrationTest {

}
