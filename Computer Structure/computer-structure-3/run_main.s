    # 212945760 Ori Dabush

    .section .rodata
scanf_number_format:
    .string "%d"
scanf_string_format:
    .string "%s"

    .text

    .globl	run_main

	.type	run_main, @function
run_main:
    pushq %rbp		            # save the old frame pointer
    movq %rsp, %rbp

    pushq %r15
    leaq (%rsp), %r15

    # setup
    pushq	%rbp		        # save the old frame pointer
	movq	%rsp, %rbp	        # create the new frame pointer
    

    subq $272, %rsp             # allocating location for both strings and their length and the option
    movq %rsp, %r9              # save the "string space" of the stack

    # get the first string
    movq $scanf_number_format, %rdi
    movq %r9, %rsi              # the length will be in the bottom of the stack string space
    pushq %r9                   # save the string space bottom address
    subq $8, %rsp
    xorq %rax, %rax             # clear %rax
    call scanf                  # get the length from the user
    addq $8, %rsp
    popq %r9
    
    movslq (%r9), %r8
    leaq 1(%rbp), %rax          # get the first byte in the current stack frame 
    movb $0, (%rax)             # make it zero (string ending '\0')


    pushq %r8                   # save the length
    movq $scanf_string_format, %rdi
    
    leaq -1(%rbp), %rsi         # get the '\0' byte address to %rsi
    subq %r8, %rsi              # go length bytes down

    pushq %rsi                  # in the bottom of the stack there is the address of the first string now (not include the length)

    # check if the length is 0, if it is - don't scan the string
    cmpq $0, %r8
    je AFTER_SCAN_FIRST_STRING

    pushq %r9                   # save the string space bottom address
    subq $8, %rsp
    xorq %rax, %rax
    call scanf                  # get the length from the user
    addq $8, %rsp
    popq %r9

AFTER_SCAN_FIRST_STRING:
    popq %rcx
    decq %rcx                   # now the address of the first string now (include the length byte) is in %rcx
    popq %rax                   # length is in %rax now
    movb %al, (%rcx)            # put length 'under' the string in the stack
    pushq %rcx                  # save the address of the first string

    # get the second string
    movq $scanf_number_format, %rdi
    pushq %rcx                  # save it again because I need it twice
    movq %r9, %rsi              # the length will be in the bottom of the stack's string space
    
    pushq %r9                   # save the string space bottom address
    subq $8, %rsp
    xorq %rax, %rax
    call scanf                  # get the length from the user
    addq $8, %rsp
    popq %r9
    
    popq %rcx

    leaq -1(%rcx), %rax         # get the first byte after the first string
    movb $0, (%rax)             # make it zero (string ending '\0')
    movslq (%r9), %rdi          # the length

    leaq -1(%rcx), %rsi         # get the '\0' byte address to %rsi

    subq %rdi, %rsi             # go length bytes down

    cmpq $0, %rdi
    je AFTER_SCAN_SECOND_STRING

    pushq %rdi                  # save the length
    pushq %rsi                  # save the address of the future string (without length)
    movq $scanf_string_format, %rdi
    
    pushq %r9                   # save the string space bottom address
    xorq %rax, %rax
    call scanf                  # get the second string from the user
    popq %r9

    popq %rsi
    popq %rdi
    
AFTER_SCAN_SECOND_STRING:
    decq %rsi
    movb %dil, (%rsi)           # put length 'under' the string

    pushq %rsi                  # save the address of the second string

    movq $scanf_number_format, %rdi
    subq $4, %rsi
    pushq %rsi
    
    pushq %r9                   # save the string space bottom address
    xorq %rax, %rax
    call scanf                  # get the length from the user
    popq %r9

    popq %rsi
    movslq (%rsi), %rdi         # get the option as a first argument for run_func
    popq %rdx                   # get the second string as a third argument for run_func
    popq %rsi                   # get the first string as a second argument for run_func

    # movq $50, %rdi
    pushq %r9
    call run_func               # call the run_func function
    popq %r9

    # ending (not very ellegant but it is what it is...)
    popq %rbp
    leaq (%r15), %rsp
    popq %r15

    # movq %rbp, %rsp
    popq %rbp
    ret
