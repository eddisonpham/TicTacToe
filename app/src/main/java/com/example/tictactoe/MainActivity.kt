package com.example.tictactoe

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TableLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Placeholder

class MainActivity : AppCompatActivity() {

    //----------------------------------Buttons Declaration-------------------------------------//

    private lateinit var constraintLayout: TableLayout
    private lateinit var b1: ImageButton
    private lateinit var b2: ImageButton
    private lateinit var b3: ImageButton
    private lateinit var b4: ImageButton
    private lateinit var b5: ImageButton
    private lateinit var b6: ImageButton
    private lateinit var b7: ImageButton
    private lateinit var b8: ImageButton
    private lateinit var b9: ImageButton

    //----------------------------------ENUM CLASSES-------------------------------------//

    enum class PLAYINGPLAYER{

        FIRST_PLAYER,
        SECOND_PLAYER

    }
    enum class WINNEROFGAME{

        PLAYER_ONE,
        PLAYER_TWO,
        NO_ONE

    }

    //----------------------------------INSTANCE VARIABLES-------------------------------------//

    var playingPlayer: PLAYINGPLAYER? = null
    var winnerOfGame: WINNEROFGAME? = null

    var player1Options: ArrayList<Int> = ArrayList()
    var player2Options: ArrayList<Int> = ArrayList()
    var allDisabledImages: ArrayList<ImageButton?> = ArrayList()
    var hexacodeArray = arrayOf("0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F")

    //----------------------------------Start Program-------------------------------------//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        constraintLayout = findViewById(R.id.constraintLayout)
        playingPlayer = PLAYINGPLAYER.FIRST_PLAYER

        b1 = findViewById(R.id.b1)
        b2 = findViewById(R.id.b2)
        b3 = findViewById(R.id.b3)
        b4 = findViewById(R.id.b4)
        b5 = findViewById(R.id.b5)
        b6 = findViewById(R.id.b6)
        b7 = findViewById(R.id.b7)
        b8 = findViewById(R.id.b8)
        b9 = findViewById(R.id.b9)

    }

    //----------------------------------When Image Clicked-------------------------------------//

    fun imageButtonTapped(view: View){
        val selectedImageBT: ImageButton = view as ImageButton
        try{
            var randomColor = "#"
            for (i in 0..5){
                randomColor+=hexacodeArray[(Math.random()*hexacodeArray.size).toInt()]
            }
            Log.i("tag",randomColor)
            constraintLayout.setBackgroundColor(Color.parseColor(randomColor))
        }catch(e:Exception){
            e.printStackTrace()
            constraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

        var optionNumber = 0

        when(selectedImageBT.id){
            R.id.b1 -> optionNumber=1
            R.id.b2 -> optionNumber=2
            R.id.b3 -> optionNumber=3
            R.id.b4 -> optionNumber=4
            R.id.b5 -> optionNumber=5
            R.id.b6 -> optionNumber=6
            R.id.b7 -> optionNumber=7
            R.id.b8 -> optionNumber=8
            R.id.b9 -> optionNumber=9
        }
        action(optionNumber, selectedImageBT)
    }

    //----------------------------------Game Functionality-------------------------------------//

    private fun action(optionNumber:Int, _selectedImageBT: ImageButton){
        var selectedImageBT = _selectedImageBT
        if(playingPlayer == PLAYINGPLAYER.FIRST_PLAYER){
            selectedImageBT.setImageResource(R.drawable.ic_launcher_background)
            player1Options.add(optionNumber)
            selectedImageBT.isEnabled = false
            allDisabledImages.add(selectedImageBT)
            playingPlayer = PLAYINGPLAYER.SECOND_PLAYER
            specifyWinner(player1Options,WINNEROFGAME.PLAYER_ONE)
        }else if (playingPlayer == PLAYINGPLAYER.SECOND_PLAYER){
            selectedImageBT.setImageResource(R.drawable.ic_launcher_foreground)
            player2Options.add(optionNumber)
            selectedImageBT.isEnabled = false
            allDisabledImages.add(selectedImageBT)
            playingPlayer = PLAYINGPLAYER.FIRST_PLAYER
            specifyWinner(player2Options,WINNEROFGAME.PLAYER_TWO)

            //Algo for comp
//            var notSelectedImageNumbers = ArrayList<Int>()
//            for (imageNumber in 1..9){
//                if (!(player1Options.contains(imageNumber))){
//                    if (!player2Options.contains(imageNumber)){
//                        notSelectedImageNumbers.add(imageNumber)
//                    }
//                }
//            }
//
//            try{
//                var randomNumber = ((Math.random()*notSelectedImageNumbers.size)).toInt()
//                var imageNumber = notSelectedImageNumbers[randomNumber]
//                when(imageNumber){
//                    1->selectedImageBT = b1
//                    2->selectedImageBT = b2
//                    3->selectedImageBT = b3
//                    4->selectedImageBT = b4
//                    5->selectedImageBT = b5
//                    6->selectedImageBT = b6
//                    7->selectedImageBT = b7
//                    8->selectedImageBT = b8
//                    9->selectedImageBT = b9
//                }
//                selectedImageBT.setImageResource(R.drawable.ic_launcher_foreground)
//                player2Options.add(imageNumber)
//                selectedImageBT.isEnabled = false
//                allDisabledImages.add(selectedImageBT)
//                playingPlayer = PLAYINGPLAYER.FIRST_PLAYER
//                specifyWinner(player2Options,WINNEROFGAME.PLAYER_TWO)
//            }catch (e:java.lang.Exception){
//                e.printStackTrace()
//            }

        }
    }

    //----------------------------------Find Winner-------------------------------------//

    private fun specifyWinner(playerOptions: ArrayList<Int>,_playingPlayer: WINNEROFGAME){
        if (playerOptions.contains(1)&&playerOptions.contains((5))&&playerOptions.contains(9)){
            winnerOfGame = _playingPlayer
        }else if (playerOptions.contains(3)&&playerOptions.contains(5)&&playerOptions.contains(7)){
            winnerOfGame = _playingPlayer
        }else{
            for (i in 0..6 step 3){
                if(playerOptions.contains(1+i)
                    &&playerOptions.contains(2+i)
                    &&playerOptions.contains(3+i)){
                    winnerOfGame = _playingPlayer
                }
                if (playerOptions.contains(1+i/3)
                    &&playerOptions.contains(4+i/3)
                    &&playerOptions.contains(7+i/3)){
                    winnerOfGame = _playingPlayer
                }
            }
        }
        if(winnerOfGame == WINNEROFGAME.PLAYER_TWO||winnerOfGame==WINNEROFGAME.PLAYER_ONE){

            var winner =  if(_playingPlayer==WINNEROFGAME.PLAYER_ONE)"One" else "Two"
            createAlert("Player $winner Wins",
            "Congratulations to Player $winner",
                AlertDialog.BUTTON_POSITIVE,"OK", false
            )
            return
        }
        checkDrawState()
    }

    //----------------------------------Create Alert MSG-------------------------------------//

    private fun createAlert(title:String,msg:String,whichButton:Int,buttonText:String,cancelable:Boolean){
        var alertDialog: AlertDialog = AlertDialog.Builder(this@MainActivity).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(msg)

        alertDialog.setButton(whichButton, buttonText,{
            dialog: DialogInterface?, which: Int ->

            resetGame()
        })
        alertDialog.setCancelable(cancelable)
        alertDialog.show()
    }

    //----------------------------------Resets The Game-------------------------------------//

    private fun resetGame(){
        player1Options.clear()
        player2Options.clear()
        allDisabledImages.clear()
        winnerOfGame = WINNEROFGAME.NO_ONE
        playingPlayer = PLAYINGPLAYER.FIRST_PLAYER

        b1.setImageResource(0)
        b2.setImageResource(0)
        b3.setImageResource(0)
        b4.setImageResource(0)
        b5.setImageResource(0)
        b6.setImageResource(0)
        b7.setImageResource(0)
        b8.setImageResource(0)
        b9.setImageResource(0)

        b1.isEnabled = true
        b2.isEnabled = true
        b3.isEnabled = true
        b4.isEnabled = true
        b5.isEnabled = true
        b6.isEnabled = true
        b7.isEnabled = true
        b8.isEnabled = true
        b9.isEnabled = true
    }

    //----------------------------------Check For Draws-------------------------------------//

    private fun checkDrawState(){

        if (allDisabledImages.size==9 && winnerOfGame!=WINNEROFGAME.PLAYER_TWO&&winnerOfGame!=WINNEROFGAME.PLAYER_TWO){
            createAlert("DRAW!!!", "No one won the game!", AlertDialog.BUTTON_POSITIVE, "OK", false)
        }
    }
}