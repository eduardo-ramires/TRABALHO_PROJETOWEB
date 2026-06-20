import axios from "axios";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import type { UsuarioDados } from "../interfaces/UsuarioDados";

const API_URL = "http://localhost:8080";

async function salvarUsuario(usuario: UsuarioDados) {
    const response = await axios.post(`${API_URL}/usuarios`, usuario);
    return response.data;
}

export function useUsuarioDadosMutate() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: salvarUsuario,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["usuario-dados"] });
        },
    });
}
