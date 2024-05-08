package com.douglas2990.aulas.applistatarefas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.douglas2990.aulas.applistatarefas.database.TarefaDAO
import com.douglas2990.aulas.applistatarefas.databinding.FragmentAdicionarTarefaBinding
import com.douglas2990.aulas.applistatarefas.model.Tarefa


class AdicionarTarefaFragment : Fragment()  {

    private var _binding: FragmentAdicionarTarefaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding !!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAdicionarTarefaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tarefa: Tarefa? = null
        val bundle: Bundle? = arguments
        if( bundle != null ){
            //tarefa = bundle.getString("tarefa","").toString() as Tarefa
            tarefa = bundle.getSerializable("tarefa") as Tarefa
            binding.editTarefa.setText( tarefa.descricao )
        }

        binding.btnSalvar.setOnClickListener {

            if( binding.editTarefa.text.isNotEmpty()){
                if( tarefa != null ){
                    editar( tarefa )
                }else{
                    salvar()
                }
            } else {
                Toast.makeText(
                    context,
                    "Preencha uma tarefa", Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

    private fun editar(tarefa: Tarefa) {

        val descricao = binding.editTarefa.text.toString()
        val tarefaAtualizar = Tarefa(
            tarefa.idTarefa, descricao, "default"
        )
        val tarefaDAO = TarefaDAO(requireContext().applicationContext)

        if( tarefaDAO.atualizar( tarefaAtualizar )){
            Toast.makeText(
                context,
                "Tarefa atualizada com sucesso", Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_adicionarTarefaFragment_to_FirstFragment)
        }

    }

    private fun salvar() {
        val descricao = binding.editTarefa.text.toString()
        val tarefa = Tarefa(
            - 1, descricao, "default"
        )

        val tarafaDAO = TarefaDAO(requireContext().applicationContext)
        if (tarafaDAO.salvar(tarefa)) {
            Toast.makeText(
                context,
                "Tarefa cadastrada com sucesso", Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_adicionarTarefaFragment_to_FirstFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}