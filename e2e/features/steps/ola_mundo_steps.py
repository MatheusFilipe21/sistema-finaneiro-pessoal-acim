from behave import given, when, then
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

# A variável 'context' é passada automaticamente pelo Behave


@given('que o navegador é inicializado')
def step_impl(context):
    assert context.driver is not None, "O driver do Selenium falhou ao inicializar."


@given('que o usuário acessa a aplicação')
def step_impl(context):
    """
    Navega para a URL base do frontend.
    """
    context.driver.get(context.base_url)


@when('o frontend se comunica com o backend')
def step_impl(context):
    """
    Este passo é essencialmente um passo de espera.
    Ele espera explicitamente até que o texto da API apareça,
    confirmando que a comunicação Frontend -> Backend foi bem-sucedida.
    """
    WebDriverWait(context.driver, 15).until(
        EC.text_to_be_present_in_element((By.TAG_NAME, "body"), "Olá Mundo!")
    )


@then('o texto "Olá Mundo!" deve estar visível na página')
def step_impl(context):
    """
    Verifica se a mensagem final está presente no corpo da página.
    """
    texto_body = context.driver.find_element(By.TAG_NAME, "body").text
    assert "Olá Mundo!" in texto_body, "Texto 'Olá Mundo!' não encontrado na página."
