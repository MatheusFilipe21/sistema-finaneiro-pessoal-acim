import { Routes } from '@angular/router';
import { OlaMundo } from './components/ola-mundo/ola-mundo';

/**
 * Define as rotas principais da aplicação.
 *
 * @author Matheus F. N. Pereira
 */
export const routes: Routes = [
  {
    path: '',
    component: OlaMundo,
  },
];
