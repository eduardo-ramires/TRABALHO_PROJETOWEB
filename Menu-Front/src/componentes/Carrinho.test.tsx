import { render, screen, fireEvent } from '@testing-library/react';
import { Carrinho } from './Carrinho';
import type { ProdutoDados } from '../interfaces/ProdutoDados';

const itensMock: ProdutoDados[] = [
    { id: 1, nome: 'Pizza Margherita', descricao: 'Desc', preco: 35.90, categoria: 'Pizza', disponibilidade: true, imagem: 'pizza.jpg' },
    { id: 2, nome: 'Refrigerante', descricao: 'Desc', preco: 8.00, categoria: 'Bebida', disponibilidade: true, imagem: 'ref.jpg' },
];

describe('Carrinho', () => {
    const onRemover = vi.fn();
    const onFechar = vi.fn();
    const onConfirmar = vi.fn();

    beforeEach(() => {
        vi.clearAllMocks();
    });

    describe('estado vazio', () => {
        it('exibe mensagem quando carrinho está vazio', () => {
            render(<Carrinho itens={[]} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            expect(screen.getByText('Seu carrinho está vazio.')).toBeInTheDocument();
        });

        it('não exibe botão de confirmar pedido quando carrinho está vazio', () => {
            render(<Carrinho itens={[]} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            expect(screen.queryByText('Confirmar Pedido')).not.toBeInTheDocument();
        });

        it('não exibe total quando carrinho está vazio', () => {
            render(<Carrinho itens={[]} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            expect(screen.queryByText('Total')).not.toBeInTheDocument();
        });
    });

    describe('renderização dos itens', () => {
        it('exibe o nome de cada item no carrinho', () => {
            render(<Carrinho itens={itensMock} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            expect(screen.getByText('Pizza Margherita')).toBeInTheDocument();
            expect(screen.getByText('Refrigerante')).toBeInTheDocument();
        });

        it('exibe o preço de cada item formatado corretamente', () => {
            render(<Carrinho itens={itensMock} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            expect(screen.getByText('R$ 35.90')).toBeInTheDocument();
            expect(screen.getByText('R$ 8.00')).toBeInTheDocument();
        });

        it('exibe botão Remover para cada item', () => {
            render(<Carrinho itens={itensMock} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            expect(screen.getAllByText('Remover')).toHaveLength(2);
        });
    });

    describe('cálculo do total', () => {
        it('calcula e exibe o total corretamente', () => {
            render(<Carrinho itens={itensMock} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            expect(screen.getByText('R$ 43.90')).toBeInTheDocument();
        });

        it('exibe total zero quando há apenas item gratuito', () => {
            const itemGratis: ProdutoDados = { id: 3, nome: 'Brinde', descricao: '', preco: 0, categoria: 'Outro', disponibilidade: true, imagem: '' };
            render(<Carrinho itens={[itemGratis]} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            // item price (R$ 0.00) + total (R$ 0.00) = 2 elementos com o mesmo valor
            expect(screen.getAllByText('R$ 0.00')).toHaveLength(2);
        });

        it('exibe total correto com um único item', () => {
            render(<Carrinho itens={[itensMock[0]]} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            // item price (R$ 35.90) + total (R$ 35.90) = 2 elementos com o mesmo valor
            expect(screen.getAllByText('R$ 35.90')).toHaveLength(2);
        });
    });

    describe('interações', () => {
        it('chama onRemover com índice 0 ao remover o primeiro item', () => {
            render(<Carrinho itens={itensMock} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            fireEvent.click(screen.getAllByText('Remover')[0]);
            expect(onRemover).toHaveBeenCalledWith(0);
        });

        it('chama onRemover com índice 1 ao remover o segundo item', () => {
            render(<Carrinho itens={itensMock} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            fireEvent.click(screen.getAllByText('Remover')[1]);
            expect(onRemover).toHaveBeenCalledWith(1);
        });

        it('chama onFechar ao clicar no botão X', () => {
            render(<Carrinho itens={itensMock} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            fireEvent.click(screen.getByText('X'));
            expect(onFechar).toHaveBeenCalledTimes(1);
        });

        it('chama onFechar ao clicar no overlay', () => {
            render(<Carrinho itens={itensMock} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            fireEvent.click(document.querySelector('.carrinho-overlay')!);
            expect(onFechar).toHaveBeenCalledTimes(1);
        });

        it('não chama onFechar ao clicar dentro do drawer', () => {
            render(<Carrinho itens={itensMock} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            fireEvent.click(document.querySelector('.carrinho-drawer')!);
            expect(onFechar).not.toHaveBeenCalled();
        });

        it('chama onConfirmar ao clicar em Confirmar Pedido', () => {
            render(<Carrinho itens={itensMock} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            fireEvent.click(screen.getByText('Confirmar Pedido'));
            expect(onConfirmar).toHaveBeenCalledTimes(1);
        });
    });

    describe('estado isEnviando', () => {
        it('exibe "Enviando..." quando isEnviando é true', () => {
            render(<Carrinho itens={itensMock} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={true} />);
            expect(screen.getByText('Enviando...')).toBeInTheDocument();
        });

        it('desabilita o botão de confirmar quando isEnviando é true', () => {
            render(<Carrinho itens={itensMock} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={true} />);
            expect(screen.getByText('Enviando...')).toBeDisabled();
        });

        it('habilita o botão de confirmar quando isEnviando é false', () => {
            render(<Carrinho itens={itensMock} onRemover={onRemover} onFechar={onFechar} onConfirmar={onConfirmar} isEnviando={false} />);
            expect(screen.getByText('Confirmar Pedido')).not.toBeDisabled();
        });
    });
});
