import axios from "axios";
import { useMutation, useQueryClient } from "@tanstack/react-query";

const API_URL = "http://localhost:8080";

async function deletarUsuario(id: number) {
    await axios.delete(`${API_URL}/usuarios/${id}`);
}

export function useUsuarioDadosDelete() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: deletarUsuario,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["usuario-dados"] });
        },
    });
}
