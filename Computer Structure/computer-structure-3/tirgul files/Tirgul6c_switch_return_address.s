	# a program that illustrat a switching the return address.
	#notice this is NOT a buffer attack!
	.section	.rodata
	#strings for printf:
att:	.string "*** You are under attack ;) ***\n"
in_ma:	.string	"You are in the main function.\n"
back:	.string	"You are sent to the main function.\n"
func:	.string	"You are in the trick function.\n"

	####################
	.text	#beginning of the code:
.globl main
	.type	main, @function
main:
	pushq	%rbx		#save value of callee-saved register

	#printing where we are:
	movq	$in_ma,%rdi
	movq	$0,%rax
	call	printf

	#calling function trick
	movq	$123, %rbx
	call	trick

	###

	#back from trick function: - printing where we are.
	movq	$in_ma,%rdi
	movq	$0,%rax
	call	printf

	#end of the main function:
	movq	$0,	%rax	#return value is zero.
	popq	%rbx		#restore callee-saved register	
	ret			#return to caller function (OS)

	#this function is rewriting the return address.
	.type	trick, @function
trick:
	pushq	%rbx		# we need a save resiter to save the return address to the main function.

	movq	8(%rsp), %rbx	#saving the old return address - to main.
				#we are saving it so we will be able to return there in the future.

	#printing where we are:
	movq	$func,%rdi
	movq	$0,%rax
	call	printf

	#changing the return address: (the trick...)
	movq	$attack,8(%rsp)	#rewrite the return address as the address of the attack function.
	
	#this function is finished - inform the user that we are back to main function
	movq	$back,%rdi
	movq	$0,%rax	
	call	printf

	
	movq	%rbx, %rax	#we are returning the return address to main as a parameter.
	popq	%rbx		#restoring the save register (%rbx) value, for the caller function.
	ret			#return to "caller function" - actually we will go to attack
	

	#this function is the attack functio we should not go to.
	.type	attack, @function
attack:
	pushq	%rbx		# we need a save register to save the return address to the main function.

	movq	%rax, %rbx	#save the return address to main.

	#print that we are here:
	movq	$att,%rdi
	movq	$0,%rax
	call	printf
	
	movq	%rbx, %rax	#moving the return address to a not save register.
	popq	%rbx		#restoring the save register (%rbx) value, for the caller function.
	jmp	*%rax		#return to main function.

	###############################################################################
	#Output:
	#> You are in the main function.
	#> You are in the trick function.
	#> You are sent to the main function.
	#> *** You are under attack ;) ***
	#> You are in the main function.
	###############################################################################

