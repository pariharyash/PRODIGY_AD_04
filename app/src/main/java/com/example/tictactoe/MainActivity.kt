package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    private lateinit var buttons: Array<Array<Button>>
    private lateinit var tvStatus: TextView
    private var playerXTurn = true
    private var board = Array(3) { IntArray(3) } // 0: empty, 1: X, 2: O

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvStatus = findViewById(R.id.tvStatus)
        buttons = Array(3) { r ->
            Array(3) { c ->
                initButton(r, c)
            }
        }
    }
    private fun initButton(row: Int, col: Int): Button {
        val button: Button = findViewById(resources.getIdentifier("button${row * 3 + col + 1}", "id", packageName))
        button.setOnClickListener {
            onCellClicked(row, col)
        }
        return button
}


    private fun onCellClicked(row: Int, col: Int) {
        if (board[row][col] != 0) {
            return
        }

        board[row][col] = if (playerXTurn) 1 else 2
        buttons[row][col].text = if (playerXTurn) "X" else "O"

        if (checkWinner()) {
            tvStatus.text = if (playerXTurn) "Player X Wins!" else "Player O Wins!"
            disableButtons()
        } else if (isBoardFull()) {
            tvStatus.text = "Draw!"
        } else {
            playerXTurn = !playerXTurn
            tvStatus.text = if (playerXTurn) "Player X's Turn" else "Player O's Turn"
        }
    }

    private fun checkWinner(): Boolean {
        // Check rows and columns
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0)
                return true
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0)
                return true
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0)
            return true
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0)
            return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == 0) {
                    return false
                }
            }
        }
        return true
    }

    private fun disableButtons() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = false
            }
        }
    }

    fun resetGame(view: View) {
        board = Array(3) { IntArray(3) }
        playerXTurn = true
        tvStatus.text = "Player X's Turn"

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
                buttons[i][j].isEnabled = true
            }
        }
    }

    fun onCellClicked(view: View) {}
}