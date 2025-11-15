import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DadosCadastroUsuarioDTO } from '../dtos/usuario/DadosCadastroUsuarioDTO';
import { UsuarioDTO } from '../dtos/usuario/UsuarioDTO';

/**
 * Serviço responsável pela comunicação com os endpoints
 * de /autenticacao no Backend.
 *
 * @author Matheus F. N. Pereira
 */
@Injectable({
  providedIn: 'root',
})
export class Autenticacao {
  /**
   * URL base para os endpoints de autenticação.
   * O 'PrefixoApiInterceptor' (do Sprint 0) adicionará o
   * prefixo '/api' (ex: http://localhost:8080/api) automaticamente.
   */
  private readonly API_URL = '/autenticacao';

  constructor(private readonly http: HttpClient) {}

  /**
   * Chama o endpoint POST /autenticacao/cadastro no backend (RF01).
   *
   * @param dados Os dados (DTO) do formulário de cadastro.
   * @returns Um Observable com o UsuarioDTO (resposta do backend).
   */
  registrar(dados: DadosCadastroUsuarioDTO): Observable<UsuarioDTO> {
    return this.http.post<UsuarioDTO>(`${this.API_URL}/cadastro`, dados);
  }
}
