import axios from "axios";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import type { ProdutoDados } from "../interfaces/ProdutoDados";

const API_URL = "http://localhost:8080";

async function editProduto(produto: ProdutoDados) {

    const response = await axios.put(
        `${API_URL}/produtos/${produto.id}`,
        produto
    );

    return response.data;
}

export function useProdutoDadosEdit() {

    const queryClient = useQueryClient();

    return useMutation({

        mutationFn: editProduto,

        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: ["produto-dados"]
            });
        }

    });
}