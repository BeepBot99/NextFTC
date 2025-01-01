package com.rowanmcalpin.nextftc.ftc.driving

import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.ftc.gamepad.GamepadEx
import com.rowanmcalpin.nextftc.ftc.gamepad.Joystick
import com.rowanmcalpin.nextftc.ftc.hardware.Drivetrain
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.Controllable
import kotlin.math.abs
import kotlin.math.max

/**
 * Drives a differential drivetrain as a tank drive.
 * @param leftMotor: The motor(s) on the left side of the drivetrain
 * @param rightMotor: The motor(s) on the right side of the drivetrain
 * @param driveJoystick: The joystick to use for forward movement
 * @param turnJoystick: The joystick to use for turning
 */
class DifferentialArcadeDriverControlled(val leftMotor: Controllable, val rightMotor: Controllable, val driveJoystick: Joystick, val turnJoystick: Joystick): Command() {

    /**
     * @param leftMotor: The motor(s) on the left side of the drivetrain
     * @param rightMotor: The motor(s) on the right side of the drivetrain
     * @param gamepad: The gamepad to use the joysticks from
     */
    constructor(leftMotor: Controllable, rightMotor: Controllable, gamepad: GamepadEx): this(leftMotor, rightMotor, gamepad.leftStick, gamepad.rightStick)

    override val isDone: Boolean = false

    override val subsystems: Set<Subsystem> = setOf(Drivetrain)

    override fun update() {
        val y = -driveJoystick.y.toDouble()
        val rx = turnJoystick.x.toDouble()

        val denominator = max(abs(y) + abs(rx), 1.0)

        leftMotor.power = (y + rx) / denominator
        rightMotor.power = (y - rx) / denominator

    }
}