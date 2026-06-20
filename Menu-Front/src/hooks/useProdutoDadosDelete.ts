import axios from "axios";
import { useMutation, useQueryClient } from "@tanstack/react-query";

const API_URL = "http://localhost:8080";

async function deleteProduto(id: number) {
    await axios.delete(`${API_URL}/produtos/${id}`);
}

export function useProdutoDadosDelete() {

    const queryClient = useQueryClient();

    return useMutation({

        mutationFn: deleteProduto,

        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: ["produto-dados"]
            });
        }

    });
}