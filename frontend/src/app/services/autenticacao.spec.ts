import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { Autenticacao } from './autenticacao';
import { DadosCadastroUsuarioDTO } from '../dtos/usuario/DadosCadastroUsuarioDTO';
import { UsuarioDTO } from '../dtos/usuario/UsuarioDTO';

/**
 * Testes unitários para o serviço {@link Autenticacao}.
 *
 * @author Matheus F. N. Pereira
 */
describe('Autenticacao', () => {
  let service: Autenticacao;
  let httpMock: HttpTestingController;

  /**
   * Configura o ambiente de testes antes de cada 'it'.
   */
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting(), Autenticacao],
    });

    service = TestBed.inject(Autenticacao);
    httpMock = TestBed.inject(HttpTestingController);
  });

  /**
   * Verifica se não ficaram requisições pendentes após cada teste.
   */
  afterEach(() => {
    httpMock.verify();
  });

  /**
   * Testa se o serviço é criado com sucesso.
   */
  it('deve ser criado', () => {
    expect(service).toBeTruthy();
  });

  /**
   * Testa o método registrar(), garantindo que o endpoint correto (POST /autenticacao/cadastro) seja chamado,
   * que o DTO enviado esteja correto e que o retorno do backend (UsuarioDTO) seja recebido conforme esperado.
   */
  it('deve registrar um usuário (POST /autenticacao/cadastro)', (done) => {
    const dadosCadastro: DadosCadastroUsuarioDTO = {
      nome: 'Matheus Filipe do Nascimento Pereira',
      email: 'matheusfnpereira@gmail.com',
      senha: 'Ab123456',
    };

    const mockResponse: UsuarioDTO = {
      id: '123e4567-e89b-12d3-a456-426614174000',
      nome: 'Matheus Filipe do Nascimento Pereira',
      email: 'matheusfnpereira@gmail.com',
    };

    service.registrar(dadosCadastro).subscribe((res) => {
      expect(res).toEqual(mockResponse);
      done();
    });

    const req = httpMock.expectOne('/autenticacao/cadastro');

    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(dadosCadastro);

    req.flush(mockResponse);
  });
});
