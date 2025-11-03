from selenium import webdriver
from selenium.webdriver.chrome.options import Options
import os

# A URL do frontend (Angular)
ANGULAR_BASE_URL = os.environ.get("FRONTEND_URL", "http://localhost:4200")


def before_all(context):
    """
    Executado antes de todos os cenários. Configura o driver do Selenium.
    """
    options = Options()

    # Não mostra a interface gráfica
    options.add_argument('--headless')
    # Desativa o modo sandbox
    options.add_argument('--no-sandbox')
    # Corrige problemas de memória
    options.add_argument('--disable-dev-shm-usage')

    # O Selenium/Behave usa o 'chromedriver' que foi instalado junto com o Chrome no postCreateCommand
    context.driver = webdriver.Chrome(options=options)
    context.base_url = ANGULAR_BASE_URL

    print(f"\n--- Iniciando E2E em: {context.base_url} (Chrome Headless) ---")


def after_all(context):
    """
    Executado depois de todos os cenários. Fecha o navegador.
    """
    if context.driver:
        context.driver.quit()
