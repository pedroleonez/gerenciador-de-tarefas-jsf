package pedroleonez.jsfff.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pedroleonez.jsfff.model.Tarefa;
import pedroleonez.jsfff.model.Prioridade;
import pedroleonez.jsfff.service.TarefaService;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class TarefaBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TarefaService service;

    private Tarefa tarefa = new Tarefa();
    private List<Tarefa> tarefas;
    private List<Tarefa> tarefasFiltradas;

    @PostConstruct
    public void init() {
        atualizarLista();
    }

    public void salvar() {
        try {
            service.salvar(tarefa);
            adicionarMensagem("Sucesso!", "Tarefa salva com êxito.");
            tarefa = new Tarefa();
            atualizarLista();
        } catch (Exception e) {
            adicionarMensagem("Erro", "Falha ao salvar tarefa: " + e.getMessage());
        }
    }

    public void prepararEdicao(Tarefa t) {
        this.tarefa = t;
    }

    public void concluir(Tarefa t) {
        try {
            service.concluir(t);
            adicionarMensagem("Concluída", "Tarefa marcada como concluída.");
            atualizarLista();
        } catch (Exception e) {
            adicionarMensagem("Erro", "Não foi possível concluir a tarefa.");
        }
    }

    public void remover(Tarefa t) {
        try {
            service.remover(t);
            adicionarMensagem("Removida", "Tarefa excluída do sistema.");
            atualizarLista();
        } catch (Exception e) {
            adicionarMensagem("Erro", "Erro ao remover: " + e.getMessage());
        }
    }

    private void atualizarLista() {
        this.tarefas = service.listarTodas();
    }

    private void adicionarMensagem(String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, resumo, detalhe));
    }

    // Getters e Setters
    public Tarefa getTarefa() { return tarefa; }
    public void setTarefa(Tarefa tarefa) { this.tarefa = tarefa; }

    public List<Tarefa> getTarefas() { return tarefas; }
    public void setTarefas(List<Tarefa> tarefas) { this.tarefas = tarefas; }

    public List<Tarefa> getTarefasFiltradas() { return tarefasFiltradas; }
    public void setTarefasFiltradas(List<Tarefa> tarefasFiltradas) { this.tarefasFiltradas = tarefasFiltradas; }

    public Prioridade[] getPrioridades() { return Prioridade.values(); }
}