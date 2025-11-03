import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { HttpClient, provideHttpClient, withInterceptors } from '@angular/common/http';

import { prefixoApiInterceptor } from './prefixo-api-interceptor';

/**
 * Testes unitários para o prefixoApiInterceptor.
 *
 * @author Matheus F. N. Pereira
 */
describe('PrefixoApiInterceptor', () => {
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(withInterceptors([prefixoApiInterceptor])),
        provideHttpClientTesting(),
      ],
    });

    httpMock = TestBed.inject(HttpTestingController);
    httpClient = TestBed.inject(HttpClient);
  });

  afterEach(() => {
    httpMock.verify();
  });

  /**
   * Testa se o interceptor adiciona o prefixo /api
   * a uma chamada relativa (ex: /ola).
   */
  it('deve adicionar o prefixo /api a chamadas relativas', () => {
    httpClient.get('/ola').subscribe();

    const req = httpMock.expectOne('/api/ola');
    expect(req.request.method).toBe('GET');
    req.flush('OK');
  });

  /**
   * Testa se o interceptor ignora URLs absolutas (https://...).
   */
  it('deve ignorar URLs absolutas', () => {
    httpClient.get('https://google.com.br').subscribe();

    const req = httpMock.expectOne('https://google.com.br');
    expect(req.request.method).toBe('GET');
    req.flush('OK');
  });
});
