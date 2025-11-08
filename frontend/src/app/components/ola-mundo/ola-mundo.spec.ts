import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';

import { OlaMundo } from './ola-mundo';

import { OlaMundo as OlaMundoService } from '../../services/ola-mundo';

/**
 * Testes unitários para o componente OlaMundo.
 *
 * @author Matheus F. N. Pereira
 */
describe('OlaMundo', () => {
  let component: OlaMundo;
  let fixture: ComponentFixture<OlaMundo>;
  let mockOlaMundoService: jasmine.SpyObj<OlaMundoService>;

  beforeEach(async () => {
    mockOlaMundoService = jasmine.createSpyObj('OlaMundoService', ['getSaudacao']);

    await TestBed.configureTestingModule({
      imports: [OlaMundo],
      providers: [{ provide: OlaMundoService, useValue: mockOlaMundoService }],
    }).compileComponents();

    fixture = TestBed.createComponent(OlaMundo);
    component = fixture.componentInstance;
  });

  /**
   * Testa se o componente foi criado com sucesso.
   */
  it('deve criar o componente', () => {
    expect(component).toBeTruthy();
    expect(component.saudacao).toBe('Carregando...');
  });

  /**
   * Testa o fluxo de sucesso (OnInit).
   */
  it('deve carregar "Olá Mundo!" no ngOnInit (Fluxo de Sucesso)', () => {
    const mockResponse = 'Olá Mundo!';
    mockOlaMundoService.getSaudacao.and.returnValue(of(mockResponse));

    fixture.detectChanges();

    expect(component.saudacao).toBe(mockResponse);
  });

  /**
   * Testa o fluxo de erro (OnInit).
   */
  it('deve mostrar mensagem de erro no ngOnInit (Fluxo de Erro)', () => {
    mockOlaMundoService.getSaudacao.and.returnValue(throwError(() => new Error('Falha na API')));

    fixture.detectChanges();

    expect(component.saudacao).toBe('Erro ao carregar dados da API.');
  });
});
