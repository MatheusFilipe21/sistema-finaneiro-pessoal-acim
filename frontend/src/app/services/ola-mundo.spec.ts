import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { OlaMundo as OlaMundoService } from './ola-mundo';
import { prefixoApiInterceptor } from '../interceptors/prefixo-api-interceptor';

/**
 * Testes unitários para o serviço OlaMundo.
 *
 * @author Matheus F. N. Pereira
 */
describe('OlaMundoService', () => {
  let servico: OlaMundoService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(withInterceptors([prefixoApiInterceptor])),
        provideHttpClientTesting(),
        OlaMundoService,
      ],
    });

    servico = TestBed.inject(OlaMundoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  /**
   * Testa se o serviço foi criado com sucesso.
   */
  it('deve ser criado', () => {
    expect(servico).toBeTruthy();
  });

  /**
   * Testa se o método getSaudacao() faz a chamada HTTP correta
   * (GET /ola) e retorna a resposta esperada.
   */
  it('deve buscar a saudação (GET /ola)', (done) => {
    const mockResponse = 'Olá Mundo!';

    servico.getSaudacao().subscribe((resposta) => {
      expect(resposta).toEqual(mockResponse);
      done();
    });

    const req = httpMock.expectOne('/api/ola');

    expect(req.request.method).toBe('GET');
    expect(req.request.responseType).toBe('text');

    req.flush(mockResponse);
  });
});
