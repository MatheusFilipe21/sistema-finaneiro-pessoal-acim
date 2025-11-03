import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

/**
 * Serviço responsável por buscar a saudação "Olá Mundo!"
 * do backend.
 *
 * @author Matheus F. N. Pereira
 */
@Injectable({
  providedIn: 'root',
})
export class OlaMundo {
  /**
   * Injeta o HttpClient global da aplicação.
   */
  private readonly http = inject(HttpClient);

  /**
   * Busca a saudação "Olá Mundo!" do backend.
   *
   * @returns Um Observable que emite a string de resposta.
   */
  public getSaudacao(): Observable<string> {
    return this.http.get('/ola', { responseType: 'text' });
  }
}
