import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OlaMundo as OlaMundoService } from '../../services/ola-mundo';

/**
 * Componente de "Olá Mundo"
 * Responsável por consumir o OlaMundoService e exibir
 * a resposta na tela.
 *
 * @author Matheus F. N. Pereira
 */
@Component({
  selector: 'app-ola-mundo',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ola-mundo.html',
  styleUrl: './ola-mundo.scss',
})
export class OlaMundo implements OnInit {
  /**
   * Injeta o OlaMundoService.
   */
  private readonly olaMundoService = inject(OlaMundoService);

  /**
   * Armazena o texto recebido da API.
   */
  public saudacao: string = 'Carregando...';

  /**
   * Método do ciclo de vida do Angular, executado quando o componente é iniciado.
   */
  ngOnInit(): void {
    this.olaMundoService.getSaudacao().subscribe({
      next: (resposta) => {
        this.saudacao = resposta;
      },
      error: (erro) => {
        this.saudacao = 'Erro ao carregar dados da API.';
        console.error(erro);
      },
    });
  }
}
