package com.example.check_the_solution_by_midili

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import com.example.check_the_solution_by_midili.databinding.ActivityMainBinding
import java.util.Timer
import java.util.TimerTask
import java.util.regex.Pattern
import kotlin.concurrent.timer
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var rnd: Boolean=Random.nextBoolean()
    private var right:Int=0
    private var nright:Int=0
    private var qty:Int=0
    private var qtytime:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        var rnd = Random.nextBoolean()
        setContentView(binding.root)
    }

    fun startClick(view:View){
        binding.chronometer.base = SystemClock.elapsedRealtime()
        binding.chronometer.start()
        binding.fonlay.setBackgroundColor(Color.WHITE)
        rnd = Random.nextBoolean()
        binding.startBtn.isEnabled=false
        binding.noBtn.isEnabled=true
        binding.yesBtn.isEnabled=true
        binding.firsttxt.text= Random.nextInt(10,99).toString()
        binding.secondtxt.text= Random.nextInt(10,99).toString()
        when(Random.nextInt(1,4)){
        1->binding.znaktxt.text="/"
        2->binding.znaktxt.text="*"
        3->binding.znaktxt.text="+"
        4->binding.znaktxt.text="-"
        }
        when(rnd){
            true->if(binding.znaktxt.text=="/"){
                binding.totaltxt.text=String.format("%.2f", (binding.firsttxt.text.toString().
                toDouble()/binding.secondtxt.text.toString().toDouble()))
            }
            else{
                when(binding.znaktxt.text){
                    "+"->binding.totaltxt.text=(binding.firsttxt.text.toString().toInt()+
                            binding.secondtxt.text.toString().toInt()).toString()
                    "*"->binding.totaltxt.text=(binding.firsttxt.text.toString().toInt()*
                            binding.secondtxt.text.toString().toInt()).toString()
                    "-"->binding.totaltxt.text=(binding.firsttxt.text.toString().toInt()-
                            binding.secondtxt.text.toString().toInt()).toString()
                }
            }
            false->if(binding.znaktxt.text=="/"){
                binding.totaltxt.text= String.format("%.2f",Random.nextDouble(-100.00,1000.00))
            }
            else{
                binding.totaltxt.text= Random.nextInt(-100,1000).toString()
            }
        }
        when(binding.znaktxt.text){
            "+"->if((binding.firsttxt.text.toString().toInt()+
                    binding.secondtxt.text.toString().toInt())==binding.totaltxt.text.toString().toInt()){
                rnd=true
            }
            "*"->if((binding.firsttxt.text.toString().toInt()*
                        binding.secondtxt.text.toString().toInt())==binding.totaltxt.text.toString().toInt()){
                rnd=true
            }
            "-"->if((binding.firsttxt.text.toString().toInt()-
                        binding.secondtxt.text.toString().toInt())==binding.totaltxt.text.toString().toInt()){
                rnd=true
            }
            "/"->if((binding.firsttxt.text.toString().toDouble()/
                        binding.secondtxt.text.toString().toDouble())==binding.totaltxt.text.toString().toDouble()){
                rnd=true
            }
        }
    }

    fun yesClick(view:View){
        binding.chronometer.stop()
        qty++
        binding.qtytxt.text=qty.toString()
        binding.startBtn.isEnabled=true
        binding.noBtn.isEnabled=false
        binding.yesBtn.isEnabled=false
        if (rnd){
            binding.fonlay.setBackgroundColor(Color.GREEN)
            right++
            binding.righttxt.text=right.toString()
            //Хронометр
            val regex = "([0-1]?\\d|2[0-3])(?::([0-5]?\\d))?(?::([0-5]?\\d))?"
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(binding.chronometer.text)
            if (matcher.find()) {
                val isHHMMSSFormat = matcher.groupCount() == 4
                if (isHHMMSSFormat) {
                    val hour = matcher.group(1).toInt()
                    val minute = matcher.group(2).toInt()
                    val second = matcher.group(3).toInt()
                    if(qtytime==0){
                        binding.maxtxt.text=(hour*3600+minute*60+second).toString()
                        binding.mintxt.text=(hour*3600+minute*60+second).toString()
                        qtytime+=(hour*3600+minute*60+second)
                    }
                    else{
                        qtytime+=(hour*3600+minute*60+second)
                        if((hour*3600+minute*60+second)>binding.maxtxt.text.toString().toInt()){
                            binding.maxtxt.text=(hour*3600+minute*60+second).toString()
                        }
                        if ((hour*3600+minute*60+second)<binding.mintxt.text.toString().toInt()){
                            binding.mintxt.text=(hour*3600+minute*60+second).toString()
                        }
                    }
                }
                else {
                    val minute = matcher.group(1).toInt()
                    val second = matcher.group(2).toInt()
                    if(qtytime==0){
                        binding.maxtxt.text=(minute*60+second).toString()
                        binding.mintxt.text=(minute*60+second).toString()
                        qtytime+=(minute*60+second)
                    }
                    else{
                        qtytime+=(minute*60+second)
                        if((minute*60+second)>binding.maxtxt.text.toString().toInt()){
                            binding.maxtxt.text=(minute*60+second).toString()
                        }
                        if ((minute*60+second)<binding.mintxt.text.toString().toInt()){
                            binding.mintxt.text=(minute*60+second).toString()
                        }
                    }
                }
            }
            binding.avgtxt.text=String.format("%.0f",(qtytime.toDouble()/right.toDouble()))
        }
        else{
            binding.fonlay.setBackgroundColor(Color.RED)
            nright++
            binding.falsetxt.text=nright.toString()
        }
        binding.percenttxt.text= String.format("%.2f",(right.toDouble()/qty.toDouble()*100))+"%"
    }

    fun noClick(view:View){
        binding.chronometer.stop()
        qty++
        binding.qtytxt.text=qty.toString()
        binding.startBtn.isEnabled=true
        binding.noBtn.isEnabled=false
        binding.yesBtn.isEnabled=false
        if (rnd){
            binding.fonlay.setBackgroundColor(Color.RED)
            nright++
            binding.falsetxt.text=nright.toString()
        }
        else{
            binding.fonlay.setBackgroundColor(Color.GREEN)
            right++
            binding.righttxt.text=right.toString()
            //Хронометр
            val regex = "([0-1]?\\d|2[0-3])(?::([0-5]?\\d))?(?::([0-5]?\\d))?"
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(binding.chronometer.text)
            if (matcher.find()) {
                val isHHMMSSFormat = matcher.groupCount() == 4
                if (isHHMMSSFormat) {
                    val hour = matcher.group(1).toInt()
                    val minute = matcher.group(2).toInt()
                    val second = matcher.group(3).toInt()
                    if(qtytime==0){
                        binding.maxtxt.text=(hour*3600+minute*60+second).toString()
                        binding.mintxt.text=(hour*3600+minute*60+second).toString()
                        qtytime+=(hour*3600+minute*60+second)
                    }
                    else{
                        qtytime+=(hour*3600+minute*60+second)
                        if((hour*3600+minute*60+second)>binding.maxtxt.text.toString().toInt()){
                            binding.maxtxt.text=(hour*3600+minute*60+second).toString()
                        }
                        if ((hour*3600+minute*60+second)<binding.mintxt.text.toString().toInt()){
                            binding.mintxt.text=(hour*3600+minute*60+second).toString()
                        }
                    }
                }
                else {
                    val minute = matcher.group(1).toInt()
                    val second = matcher.group(2).toInt()
                    if(qtytime==0){
                        binding.maxtxt.text=(minute*60+second).toString()
                        binding.mintxt.text=(minute*60+second).toString()
                        qtytime+=(minute*60+second)
                    }
                    else{
                        qtytime+=(minute*60+second)
                        if((minute*60+second)>binding.maxtxt.text.toString().toInt()){
                            binding.maxtxt.text=(minute*60+second).toString()
                        }
                        if ((minute*60+second)<binding.mintxt.text.toString().toInt()){
                            binding.mintxt.text=(minute*60+second).toString()
                        }
                    }
                }
            }
            binding.avgtxt.text=String.format("%.0f",(qtytime.toDouble()/right.toDouble()))
        }
        binding.percenttxt.text= String.format("%.2f",(right.toDouble()/qty.toDouble()*100))+"%"
    }
}