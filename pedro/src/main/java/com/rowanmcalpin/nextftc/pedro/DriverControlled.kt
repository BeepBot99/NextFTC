package com.rowanmcalpin.nextftc.pedro

import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.ftc.gamepad.GamepadEx
import com.rowanmcalpin.nextftc.ftc.gamepad.Joystick
import com.rowanmcalpin.nextftc.ftc.hardware.Drivetrain

/**
 * Uses the joystick inputs to drive the robot
 *
 * @param driveJoystick The joystick to use for forward and strafe movement
 * @param turnJoystick The joystick to use for turning
 * @param robotCentric Whether to use robot centric or field centric movement
 * @param invertDrive whether to invert the drive joystick
 * @param invertTurn whether to invert the turn joystick
 * @param invertStrafe whether to invert the strafe joystick
 */
class DriverControlled @JvmOverloads constructor(val driveJoystick: Joystick, val turnJoystick: Joystick, val robotCentric: Boolean,
    val invertDrive: Boolean = false, val invertTurn: Boolean = false, val invertStrafe: Boolean = false): Command() {

    @JvmOverloads
    /**
     * @param driveJoystick The joystick to use for forward and strafe movement
     * @param turnJoystick The joystick to use for turning
     * @param invertDrive whether to invert the drive joystick
     * @param invertTurn whether to invert the turn joystick
     * @param invertStrafe whether to invert the strafe joystick
     */
    constructor(driveJoystick: Joystick, turnJoystick: Joystick, invertDrive: Boolean = false, invertTurn: Boolean = false, invertStrafe: Boolean = false): this(driveJoystick, turnJoystick, true, invertDrive, invertTurn, invertStrafe)

    @JvmOverloads
    /**
     * @param gamepad The gamepad to use the joysticks from
     * @param robotCentric Whether to use robot centric or field centric movement
     * @param invertDrive whether to invert the drive joystick
     * @param invertTurn whether to invert the turn joystick
     * @param invertStrafe whether to invert the strafe joystick
     */
    constructor(gamepad: GamepadEx, robotCentric: Boolean, invertDrive: Boolean = false, invertTurn: Boolean = false, invertStrafe: Boolean = false): this(gamepad.leftStick, gamepad.rightStick, robotCentric, invertDrive, invertTurn, invertStrafe)

    @JvmOverloads
    /**
     * @param gamepad The gamepad to use the joysticks from
     * @param invertDrive whether to invert the drive joystick
     * @param invertTurn whether to invert the turn joystick
     * @param invertStrafe whether to invert the strafe joystick
     */
    constructor(gamepad: GamepadEx, invertDrive: Boolean = false, invertTurn: Boolean = false, invertStrafe: Boolean = false): this(gamepad.leftStick, gamepad.rightStick, true, invertDrive, invertTurn, invertStrafe)

    override val isDone: Boolean = false

    override val subsystems: Set<Subsystem> = setOf(Drivetrain)

    override fun start() {
        if (PedroData.follower == null) {
            throw FollowerNotInitializedException()
        }
        PedroData.follower!!.startTeleopDrive()
    }
    
    override fun update() {
        PedroData.follower!!.setTeleOpMovementVectors(driveJoystick.y.toDouble() * if(invertDrive) -1 else 1,
            driveJoystick.x.toDouble() * if(invertStrafe) -1 else 1, turnJoystick.x.toDouble() * if(invertTurn) -1 else 1, robotCentric)
    }
}