export interface UsuarioDados {
    id?: number;
    nome?: string;
    mesa?: number;
    tipo: "ADM" | "USUARIO";
    senha?: string;
}
