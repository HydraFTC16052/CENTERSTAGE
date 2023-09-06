package org.firstinspires.ftc.teamcode.Oficial;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class TeleOpModificado extends LinearOpMode {

    private DcMotor RMF, RMB, LMF, LMB;
    private double velocidade = 0.50;
    private boolean potenciaMax = false;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        double TPdegiro = 0.8;
        double PTdegiro = 1.0;

        RMF = hardwareMap.get(DcMotor.class, "RMF");
        RMB = hardwareMap.get(DcMotor.class, "RMB");
        LMF = hardwareMap.get(DcMotor.class, "LMF");
        LMB = hardwareMap.get(DcMotor.class, "LMB");

        RMF.setDirection(DcMotorSimple.Direction.FORWARD);
        RMB.setDirection(DcMotorSimple.Direction.FORWARD);
        LMF.setDirection(DcMotorSimple.Direction.REVERSE);
        LMB.setDirection(DcMotorSimple.Direction.REVERSE);

        RMF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RMB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LMF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LMB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {

            boolean Rb = gamepad1.right_bumper;
            boolean Lb = gamepad1.left_bumper;
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rotacao = gamepad1.right_stick_x;

            double potenciaRF = y - x - rotacao;
            double potenciaRB = y + x - rotacao;
            double potenciaLF = y + x + rotacao;
            double potenciaLB = y - x + rotacao;

            potenciaRF *= velocidade;
            potenciaRB *= velocidade;
            potenciaLF *= velocidade;
            potenciaLB *= velocidade;

            potenciaRF = Math.max(-1.0, Math.min(1.0, potenciaRF));
            potenciaRB = Math.max(-1.0, Math.min(1.0, potenciaRB));
            potenciaLF = Math.max(-1.0, Math.min(1.0, potenciaLF));
            potenciaLB = Math.max(-1.0, Math.min(1.0, potenciaLB));

            RMF.setPower(potenciaRF);
            RMB.setPower(potenciaRB);
            LMF.setPower(potenciaLF);
            LMB.setPower(potenciaLB);

            if (gamepad1.x) {
                // Armazenar o valor atual
                velocidade = 0.25;
                potenciaMax = false;
                telemetry.addData("Modo", "Modo X ativado (0.25)");
            } else if (gamepad1.a) {
                // Armazenar o valor atual
                velocidade = 0.50;
                potenciaMax = false;
                telemetry.addData("Modo", "Modo A ativado (0.50)");
            } else if (gamepad1.b) {
                // Armazenar o valor atual
                velocidade = 0.75;
                potenciaMax = false;
                telemetry.addData("Modo", "Modo B ativado (0.75)");
            }

            if (gamepad1.right_trigger > 0.5) {
                velocidade = 1.0;
                potenciaMax = true;
                telemetry.addData("Modo", "Potência máxima ativada!");

            }

            if (Lb == true) {

                runtime.reset();

                while(runtime.seconds() < TPdegiro) {

                    RMB.setPower(PTdegiro); //Ainda nao ajeitei qual motor que vai girar pra onde
                    RMF.setPower(PTdegiro);
                    LMB.setPower(-1.0);
                    LMF.setPower(-1.0);

                }
            }

            if (Rb == true) {

                runtime.reset();

                while(runtime.seconds() < TPdegiro) {

                    LMB.setPower(PTdegiro); //Ainda nao ajeitei qual motor que vai girar pra onde
                    LMF.setPower(PTdegiro);
                    RMB.setPower(-1.0);
                    RMF.setPower(-1.0);

                }
            }



            telemetry.addData("Fator de Multiplicação de Velocidade", velocidade);
            telemetry.addData("Potência Máxima Ativada", potenciaMax);
            telemetry.update();
        }
    }
}