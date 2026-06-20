import axios from "axios";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import type { ProdutoDados } from "../interfaces/ProdutoDados";

const API_URL = "http://localhost:8080";

async function salvarProduto(produto: ProdutoDados) {

    const response = await axios.post(
        `${API_URL}/produtos`,
        produto
    );

    return response.data;
}

export function useProdutoDadosMutate() {

    const queryClient = useQueryClient();

    return useMutation({

        mutationFn: salvarProduto,

        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: ["produto-dados"]
            });
        }

    });
}