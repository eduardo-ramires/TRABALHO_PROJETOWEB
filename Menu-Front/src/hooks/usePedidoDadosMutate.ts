import axios from "axios";
import { useMutation } from "@tanstack/react-query";
import type { PedidoDados } from "../interfaces/PedidoDados";

const API_URL = "http://localhost:8080";

async function criarPedido(pedido: PedidoDados) {
    const response = await axios.post(`${API_URL}/pedidos`, pedido);
    return response.data;
}

export function usePedidoDadosMutate() {
    return useMutation({
        mutationFn: criarPedido,
    });
}
